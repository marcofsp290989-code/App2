package com.adaptivepower.ai

import kotlin.math.*
import java.util.concurrent.atomic.AtomicBoolean

class AIManager {
    private var kState = 0f
    private var kCov = 1f
    private val kProcessNoise = 1e-3f
    private val kMeasurementNoise = 1e-1f

    private var ema = 0f
    private val emaAlpha = 0.25f

    private val sampleTimes = mutableListOf<Long>()
    private val sampleCurrents = mutableListOf<Float>()
    private val maxSamples = 200

    private var kp = 0.8f
    private var ki = 0.02f
    private var kd = 0.12f
    private var integral = 0f
    private var lastError = 0f
    private var lastTime = System.currentTimeMillis()

    @Volatile var stabilizedCurrent = 0f
    @Volatile var rawCurrent = 0f
    @Volatile var predictedTimeToFullMinutes: Float = -1f
    @Volatile var stabilityScore: Float = 0f

    private val running = AtomicBoolean(true)

    fun stop() { running.set(false) }

    fun addSample(currentMa: Float, voltageV: Float, batteryPct: Int) {
        rawCurrent = currentMa
        val now = System.currentTimeMillis()

        val measurement = currentMa
        kCov += kProcessNoise
        val kGain = kCov / (kCov + kMeasurementNoise)
        kState = kState + kGain * (measurement - kState)
        kCov = (1 - kGain) * kCov
        val kalman = kState

        ema = if (ema == 0f) kalman else (emaAlpha * kalman + (1 - emaAlpha) * ema)

        sampleTimes.add(now)
        sampleCurrents.add(kalman)
        if (sampleTimes.size > maxSamples) {
            sampleTimes.removeAt(0)
            sampleCurrents.removeAt(0)
        }

        val variance = calculateVariance(sampleCurrents)
        val desiredStability = 0.9f
        val error = desiredStability - stabilityFromVariance(variance)
        val dt = max(1f, (now - lastTime).toFloat() / 1000f)

        integral += error * dt
        val derivative = (error - lastError) / dt
        val output = kp * error + ki * integral + kd * derivative
        lastError = error
        lastTime = now

        val factor = 1f + output.coerceIn(-0.5f, 0.5f)
        stabilizedCurrent = (ema * factor).coerceAtLeast(0f)

        stabilityScore = stabilityFromVariance(variance)
        predictedTimeToFullMinutes = estimateTimeToFullMinutes(sampleCurrents, voltageV, batteryPct)
    }

    private fun calculateVariance(values: List<Float>): Float {
        if (values.isEmpty()) return 0f
        val mean = values.average().toFloat()
        var s = 0f
        for (v in values) s += (v - mean) * (v - mean)
        return s / values.size
    }

    private fun stabilityFromVariance(variance: Float): Float {
        val norm = 1f / (1f + variance / 50000f)
        return norm.coerceIn(0f, 1f)
    }

    private fun estimateTimeToFullMinutes(currents: List<Float>, voltageV: Float, batteryPct: Int): Float {
        if (currents.isEmpty() || voltageV <= 0f) return -1f
        val avgMa = currents.takeLast(30).average().toFloat().coerceAtLeast(10f)
        val capacity = 4500f
        val remainingMah = capacity * (100 - batteryPct) / 100f
        val hours = remainingMah / avgMa
        return hours * 60f
    }

    fun tunePid(kp: Float, ki: Float, kd: Float) {
        this.kp = kp; this.ki = ki; this.kd = kd
    }
}
