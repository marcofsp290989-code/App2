
package com.adaptive.power

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue

class BatteryMonitorService: Service() {

    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private lateinit var ai: AIEngine
    private val queue = ConcurrentLinkedQueue<BatterySample>()

    override fun onCreate() {
        super.onCreate()
        ai = AIEngine(applicationContext)
        startForegroundServiceWithNotification()
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        scope.launch { processLoop() }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startForegroundServiceWithNotification() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "adaptive_power_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Adaptive Power", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(channel)
        }
        val notif: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("AdaptivePowerEngine")
            .setContentText("Monitoring battery and usage")
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .build()

        startForeground(1, notif)
    }

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) // mV
            val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) // tenths of a degree C
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
            val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val percent = if (level >=0 && scale > 0) (level*100/scale) else -1

            val bm = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            // Some devices support getLongProperty for current microamps
            val currentNow = try { bm.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) } catch (e: Exception){0L}

            val sample = BatterySample(System.currentTimeMillis(), percent, voltage, temp, currentNow, plugged, status)
            queue.add(sample)
        }
    }

    private suspend fun processLoop() {
        while (isActive) {
            val sample = queue.poll()
            if (sample != null) {
                DataLogger.logSample(applicationContext, sample)
                ai.feedSample(sample)
                val action = ai.decideAction(sample)
                // execute action: limited on non-root devices. We only adjust software-controlled knobs where possible.
                ChargingController.evaluateAndAct(applicationContext, action, sample)
            } else {
                delay(1000)
            }
        }
    }
}

data class BatterySample(val ts: Long, val level: Int, val voltageMv: Int, val tempTenthsC: Int, val currentNowMicroA: Long, val plugged: Int, val status: Int)
