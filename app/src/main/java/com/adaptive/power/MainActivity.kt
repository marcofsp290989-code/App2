
package com.adaptive.power

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var statusTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.button_start)
        stopBtn = findViewById(R.id.button_stop)
        statusTv = findViewById(R.id.text_status)

        startBtn.setOnClickListener {
            startService(Intent(this, BatteryMonitorService::class.java))
            statusTv.text = "Service started"
        }
        stopBtn.setOnClickListener {
            stopService(Intent(this, BatteryMonitorService::class.java))
            statusTv.text = "Service stopped"
        }

        // Simple UI heartbeat that updates while activity visible
        lifecycleScope.launch {
            while(true) {
                delay(2000)
                statusTv.append(".")
            }
        }
    }
}
