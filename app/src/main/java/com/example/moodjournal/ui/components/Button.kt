package com.example.moodjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.theme.Terracotta

@Composable
fun FilledButton(onClick: () -> Unit, text : String, enabled: Boolean = true) {
    Button(
        onClick = { onClick() },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun GhostButton(onClick: () -> Unit, text : String, modifier: Modifier, isSelected: Boolean) {
    OutlinedButton(
        onClick = { onClick() },
        shape = CircleShape,
        border = BorderStroke(width= 1.dp, color= Color(0xFF231F1B).copy(alpha = 0.1f)),
        modifier = modifier.fillMaxWidth(),
        colors = if (isSelected) ButtonDefaults.buttonColors(containerColor = Terracotta, contentColor = Color.White) else ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF231F1B)),

    ) {
        Text(text)
    }
}