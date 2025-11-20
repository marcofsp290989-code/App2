package com.adaptivepower.monitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class BatteryMonitor(private val ctx: Context) {
    fun readBatteryVoltage(): Float {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = ctx.registerReceiver(null, ifilter)
        val voltage = batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1
        return voltage / 1000.0f
    }

    fun readBatteryCurrent(): Int {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = ctx.registerReceiver(null, ifilter)
        // Try common extras
        val currentNow = batteryStatus?.getIntExtra("current_now", -1) ?: -1
        if (currentNow != -1) return currentNow
        val alt = batteryStatus?.getIntExtra(BatteryManager.EXTRA_CURRENT_NOW, -1) ?: -1
        if (alt != -1) return alt
        return -1
    }

    fun readTemperature(): Float {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = ctx.registerReceiver(null, ifilter)
        val temp = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1
        return temp / 10.0f
    }

    fun readBatteryPercentage(): Int {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = ctx.registerReceiver(null, ifilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: 100
        return ((level.toFloat() / scale) * 100).toInt()
    }
}
