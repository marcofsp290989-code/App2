
package com.a52s.superai.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a52s.superai.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}
