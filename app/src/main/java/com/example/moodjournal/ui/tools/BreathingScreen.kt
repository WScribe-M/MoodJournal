package com.example.moodjournal.ui.tools

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BreathingScreen(onClose: () -> Unit) {
    var isInhaling by remember { mutableStateOf(true) }

    val size by animateFloatAsState(
        targetValue = if (isInhaling) 250f else 100f,
        animationSpec = tween(durationMillis = 4000),
        label = "breathing"
    )

    // alterne entre inspiration et expiration toutes les 4 secondes
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            isInhaling = !isInhaling
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            if (isInhaling) "Inspire..." else "Expire...",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(Color(0xFF7BA098))
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onClose) {
            Text("Terminer")
        }
    }

}