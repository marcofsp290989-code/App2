package com.adaptivepower.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adaptivepower.ai.AIManager
import com.adaptivepower.monitor.BatteryMonitor
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChargingControlScreen(viewModel: ChargingViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("AI Charging Control", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("AI Charge Stabilized Boost")
            Switch(checked = viewModel.aiEnabled, onCheckedChange = { viewModel.toggleAI() })
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Métricas em tempo real")
                Spacer(Modifier.height(8.dp))
                Text("Corrente: ${'$'}{viewModel.current} mA")
                Text("Tensão: ${'$'}{viewModel.voltage} V")
                Text("Temperatura: ${'$'}{viewModel.temp} °C")
                Text("Potência: ${'$'}{viewModel.power} W")
                Text("Stability: ${'$'}{viewModel.stability}")
            }
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) { Canvas(modifier = Modifier.fillMaxSize()) { } }
    }
}
