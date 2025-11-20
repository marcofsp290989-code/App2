package com.adaptivepower.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("Adaptive Power Engine") }) }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Gestão em tempo real", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))
            ChargingControlCard()
        }
    }
}

@Composable
fun ChargingControlCard() {
    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("AI Charge Stabilized Boost", style = MaterialTheme.typography.subtitle1)
            Spacer(Modifier.height(8.dp))
            var enabled by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(if (enabled) "Estado: Ativado" else "Estado: Desativado")
                Switch(checked = enabled, onCheckedChange = { enabled = it })
            }
            Spacer(Modifier.height(8.dp))
            Text("Corrente: -- mA | Tensão: -- V | Temp: -- °C")
        }
    }
}
