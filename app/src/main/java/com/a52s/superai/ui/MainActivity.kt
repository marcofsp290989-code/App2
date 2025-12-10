
package com.a52s.superai.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a52s.superai.databinding.ActivityMainBinding
import com.a52s.superai.service.AIMonitorService

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        startService(Intent(this, AIMonitorService::class.java))

        b.btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}
