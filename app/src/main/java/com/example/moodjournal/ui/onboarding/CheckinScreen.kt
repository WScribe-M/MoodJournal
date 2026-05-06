package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun CheckinScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
){
    var emoji by remember { mutableStateOf("") }
    var intensity by remember { mutableStateOf(50) }
    var note by remember { mutableStateOf("") }

    // Le to crée une paire — emotion.first donne le nom et emotion.second donne l'emoji.
    val emotions = listOf(
        "joie" to "🌞",
        "calme" to "🌿",
        "fatigué" to "🌙",
        "triste" to "🌧",
        "anxieux" to "🌪",
        "en colère" to "🔥"
    )

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
    ) {
        LazyRow(
            Modifier.fillMaxWidth(),
        ) {
            items(emotions) {
                FilterChip(
                    selected = emoji == it.first,
                    onClick = { emoji = it.first },
                    label = { Text("${it.second} ${it.first}") }
                )
            }
        }
        Text("Intensité : $intensity")
        Slider(
            value = intensity.toFloat(),
            onValueChange = { intensity = it.toInt() },
            valueRange = 0f..100f
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Une note (optionnel)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (emoji.isNotEmpty()){
            Button( onClick = {
                viewModel.updateCheckin(emoji, intensity, note)
                onNext()
            } ) {
                Text("Continuer")
            }
        }
    }
}