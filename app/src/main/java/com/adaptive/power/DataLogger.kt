
package com.adaptive.power

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object DataLogger {
    private const val FILENAME = "battery_history.csv"

    fun logSample(ctx: Context, s: BatterySample) {
        try {
            val dir = ctx.filesDir
            val f = File(dir, FILENAME)
            val header = "ts,level,voltage_mv,temp_tenths_c,current_uv,plugged,status\n"
            if (!f.exists()) f.writeText(header)
            val line = "${s.ts},${s.level},${s.voltageMv},${s.tempTenthsC},${s.currentNowMicroA},${s.plugged},${s.status}\n"
            f.appendText(line)
        } catch (e: Exception) {
            // fail silently to avoid crashing service
        }
    }

    fun readHistory(ctx: Context): String {
        val f = File(ctx.filesDir, FILENAME)
        return if (f.exists()) f.readText() else ""
    }

    fun exportZip(ctx: Context, out: File) {
        // placeholder: export logs to zip - implementation omitted for brevity
    }
}
