
package com.adaptive.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdaptiveAIHome()
        }
    }
}

@Composable
fun AdaptiveAIHome() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Adaptive AI Device Manager") }) }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("App base gerada com sucesso", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
