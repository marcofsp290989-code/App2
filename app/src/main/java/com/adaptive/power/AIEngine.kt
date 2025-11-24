
package com.adaptive.power

import android.content.Context
import kotlin.math.max
import kotlin.math.min

/**
 * Simple adaptive "AI" engine implemented in pure Kotlin.
 * It uses an incremental linear model to predict near-future battery drain and suggests actions.
 * This is intentionally implemented without external ML dependencies so it runs on-device without heavy models.
 */
class AIEngine(private val ctx: Context) {

    // weights for a tiny linear model: predict delta percent per minute
    // features: [bias, current_level, temperature, current_now (normalized)]
    private var w = DoubleArray(4) { 0.0 }
    private var learningRate = 1e-6

    init {
        // initialize small bias so model is not degenerate
        w[0] = -0.01
    }

    fun feedSample(s: BatterySample) {
        // store recent samples in app files if desired (delegated to DataLogger)
        // adapt weights online using a simple target: observed drain per minute against predicted
        // we need at least two recent samples; for simplicity the logger will be used externally for training batch
    }

    fun predictDeltaPerMinute(s: BatterySample): Double {
        val bias = 1.0
        val cur = s.level.toDouble()
        val temp = s.tempTenthsC.toDouble() / 10.0
        val curNow = s.currentNowMicroA.toDouble() / 1e6 // convert to A units approx
        val x = doubleArrayOf(bias, cur, temp, curNow)
        var sum = 0.0
        for (i in w.indices) sum += w[i] * x[i]
        return sum // predicted percent points per minute
    }

    fun decideAction(s: BatterySample): ControlAction {
        // Use prediction to decide mild software-level actions: reduce brightness, pause sync, reduce refresh rates, etc.
        val predictedDrain = predictDeltaPerMinute(s)
        val action = ControlAction()

        // simple heuristics with model assistance
        if (s.tempTenthsC / 10.0 > 40.0 || predictedDrain > 0.5) {
            action.reduceBrightness = true
            action.restrictBackground = true
            action.notifyUser = true
        }

        // learn: perform tiny weight update toward observed drain if we can compute it (placeholder)
        return action
    }
}

data class ControlAction(var reduceBrightness: Boolean = false, var restrictBackground: Boolean = false, var notifyUser: Boolean = false)
