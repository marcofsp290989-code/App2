
package com.a52s.superai.service

import android.app.*
import android.content.*
import android.os.*
import androidx.core.app.NotificationCompat
import com.a52s.superai.ai.AIEngine
import com.a52s.superai.model.BatterySample
import com.a52s.superai.storage.DataLogger

class AIMonitorService : Service() {

    private val ai by lazy { AIEngine(this) }
    private var lastSample: BatterySample? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startForegroundService() {
        val id = "a52s_channel"
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(NotificationChannel(id, "A52s AI", NotificationManager.IMPORTANCE_LOW))
        val notif = NotificationCompat.Builder(this, id)
            .setContentTitle("AI Monitoring active")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .build()
        startForeground(1, notif)
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(c: Context?, i: Intent?) {
            val level = i?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val temp = i?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1
            val volt = i?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1
            val plugged = i?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1

            val s = BatterySample(System.currentTimeMillis(), level, volt, temp, plugged)
            lastSample = s
            DataLogger.log(this@AIMonitorService, s)
            ai.process(s)
        }
    }
}
