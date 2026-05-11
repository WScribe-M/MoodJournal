package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onVisibilityChangedNode
import androidx.compose.ui.unit.dp
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun ToolsScreen(
    onTool: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Outils bien-être", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { onTool("breathing") }) {
            Text("Exercice de respiration")
        }
        Button(onClick = { onTool("gratitude") }) {
            Text("5 choses positives")
        }
    }
}