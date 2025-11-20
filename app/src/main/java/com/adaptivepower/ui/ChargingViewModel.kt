package com.adaptivepower.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adaptivepower.ai.AIManager
import com.adaptivepower.monitor.BatteryMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChargingViewModel : ViewModel() {
    private val ai = AIManager()
    private var monitor: BatteryMonitor? = null

    var aiEnabled = false
        private set
    var current: Float = 0f
    var voltage: Float = 0f
    var temp: Float = 0f
    var power: Float = 0f
    var stability: Float = 0f

    fun start(ctx: android.content.Context) {
        if (monitor == null) monitor = BatteryMonitor(ctx)
        startSampling()
    }

    fun toggleAI() {
        aiEnabled = !aiEnabled
    }

    private fun startSampling() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    val v = monitor?.readBatteryVoltage() ?: 0f
                    val c = monitor?.readBatteryCurrent() ?: -1
                    val p = monitor?.readTemperature() ?: 0f
                    val pct = monitor?.readBatteryPercentage() ?: 0
                    if (c > 0) {
                        ai.addSample(c.toFloat(), v, pct)
                        current = ai.stabilizedCurrent
                        stability = ai.stabilityScore
                        voltage = v
                        temp = p
                        power = (current * voltage) / 1000f
                    }
                } catch (e: Exception) {
                    // ignore
                }
                delay(250)
            }
        }
    }
}
