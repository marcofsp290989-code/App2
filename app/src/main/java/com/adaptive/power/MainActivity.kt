
package com.adaptive.power

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}

@Composable
fun AppRoot() {
    MaterialTheme {
        Scaffold(topBar = { TopAppBar(title = { Text("Adaptive Power Engine") }) }) {
            Surface(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("Versão final base - Adaptive Power Engine v4", style = MaterialTheme.typography.titleLarge)
                    Text("Concede Usage Access para deteção de perfil na primeira execução.", modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
