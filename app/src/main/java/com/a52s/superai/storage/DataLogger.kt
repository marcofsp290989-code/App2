
package com.a52s.superai.storage

import android.content.Context
import com.a52s.superai.model.BatterySample
import java.io.File

object DataLogger {
    private const val FILE = "history.csv"

    fun log(ctx: Context, s: BatterySample) {
        val f = File(ctx.filesDir, FILE)
        if (!f.exists()) f.writeText("ts,level,volt,temp,plugged
")
        f.appendText("${s.ts},${s.level},${s.voltage},${s.temp},${s.plugged}
")
    }
}
