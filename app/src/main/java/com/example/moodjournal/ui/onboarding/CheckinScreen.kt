package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.EMOTIONS
import com.example.moodjournal.ui.components.FilledButton
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.components.SoftButton
import com.example.moodjournal.ui.components.emotionById
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun CheckinScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
) {
    var step       by remember { mutableIntStateOf(0) }
    var emotionId  by remember { mutableStateOf("") }
    var intensity  by remember { mutableIntStateOf(60) }
    var note       by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            onBack = { if (step > 0) step-- },
            endContent = {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(Cream2, CircleShape)
                        .clickable { /* close / retour home */ },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("✕", style = MaterialTheme.typography.bodySmall, color = Ink2)
                }
            },
        )

        // Barre de progression
        Row(
            modifier = Modifier.padding(horizontal = 26.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            listOf(0, 1, 2).forEach { i ->
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(if (i == step) 32.dp else 22.dp)
                        .background(
                            when {
                                i == step -> Terracotta
                                i < step  -> Terracotta.copy(alpha = 0.45f)
                                else      -> Cream2
                            },
                            RoundedCornerShape(2.dp),
                        ),
                )
            }
        }

        Spacer(Modifier.height(26.dp))

        when (step) {
            0 -> EmotionStep(
                selected = emotionId,
                onSelect = { id ->
                    emotionId = id
                    step = 1
                },
            )
            1 -> IntensityStep(
                emotionId = emotionId,
                intensity = intensity,
                onIntensityChange = { intensity = it },
                onNext = { step = 2 },
            )
            2 -> NoteStep(
                note = note,
                onNoteChange = { note = it },
                onSkip = {
                    viewModel.updateCheckin(emotionId, intensity, "")
                    onNext()
                },
                onSend = {
                    viewModel.updateCheckin(emotionId, intensity, note)
                    onNext()
                },
            )
        }
    }
}

@Composable
private fun EmotionStep(selected: String, onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
    ) {
        Text("ÉTAPE 01", style = MaterialTheme.typography.labelSmall, color = Ink3)
        Spacer(Modifier.height(8.dp))
        Text("Comment tu te\nsens là ?", style = MaterialTheme.typography.titleLarge, color = Ink)
        Spacer(Modifier.height(4.dp))
        Text("Choisis l'émotion la plus proche.", style = MaterialTheme.typography.bodyMedium, color = Ink2)
        Spacer(Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(EMOTIONS) { e ->
                val on = selected == e.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (on) e.bgColor else Paper, RoundedCornerShape(18.dp))
                        .border(
                            if (on) 1.5.dp else 1.dp,
                            if (on) e.inkColor else Line,
                            RoundedCornerShape(18.dp),
                        )
                        .clickable { onSelect(e.id) }
                        .padding(14.dp, 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(e.emoji, style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp))
                    Text(e.label, style = MaterialTheme.typography.labelMedium, color = Ink)
                }
            }
        }
    }
}

@Composable
private fun IntensityStep(
    emotionId: String,
    intensity: Int,
    onIntensityChange: (Int) -> Unit,
    onNext: () -> Unit,
) {
    val e = emotionById(emotionId)
    val lvlLabel = when {
        intensity < 33 -> "Légèrement"
        intensity < 66 -> "Modérément"
        else           -> "Intensément"
    }
    val orbSize = (80 + (intensity / 100f) * 70).dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
    ) {
        Text("ÉTAPE 02", style = MaterialTheme.typography.labelSmall, color = Ink3)
        Spacer(Modifier.height(8.dp))
        Text("À quelle intensité ?", style = MaterialTheme.typography.titleLarge, color = Ink)
        Spacer(Modifier.height(4.dp))
        Text("Déplace le curseur selon ce que tu ressens.", style = MaterialTheme.typography.bodyMedium, color = Ink2)

        Spacer(Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            // Blob émotionnel
            Box(
                modifier = Modifier
                    .size(orbSize)
                    .background(
                        e?.bgColor ?: Cream2,
                        CircleShape,
                    )
                    .border(3.dp, e?.inkColor ?: Ink3, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(e?.emoji ?: "😶", style = MaterialTheme.typography.titleLarge.copy(fontSize = (orbSize.value * 0.42).sp))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(e?.label ?: "", style = MaterialTheme.typography.headlineMedium, color = e?.inkColor ?: Ink)
                Text("$lvlLabel · $intensity%", style = MaterialTheme.typography.bodySmall, color = Ink3)
            }

            Column {
                Slider(
                    value = intensity.toFloat(),
                    onValueChange = { onIntensityChange(it.toInt()) },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = e?.inkColor ?: Terracotta,
                        activeTrackColor = e?.inkColor ?: Terracotta,
                        inactiveTrackColor = Cream2,
                    ),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Léger", style = MaterialTheme.typography.bodySmall, color = Ink3)
                    Text("Intense", style = MaterialTheme.typography.bodySmall, color = Ink3)
                }
            }
        }

        Spacer(Modifier.weight(1f))

        FilledButtonBlock(
            onClick = onNext,
            text = "Continuer →",
        )

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun NoteStep(
    note: String,
    onNoteChange: (String) -> Unit,
    onSkip: () -> Unit,
    onSend: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
    ) {
        Text("ÉTAPE 03", style = MaterialTheme.typography.labelSmall, color = Ink3)
        Spacer(Modifier.height(8.dp))
        Text("Une pensée à partager ?", style = MaterialTheme.typography.titleLarge, color = Ink)
        Spacer(Modifier.height(4.dp))
        Text("Optionnel — juste quelques mots si tu veux.", style = MaterialTheme.typography.bodyMedium, color = Ink2)
        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Paper, RoundedCornerShape(18.dp))
                .border(1.dp, Line, RoundedCornerShape(18.dp))
                .padding(16.dp),
        ) {
            BasicTextField(
                value = note,
                onValueChange = onNoteChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Ink),
                cursorBrush = SolidColor(Terracotta),
                modifier = Modifier.fillMaxSize(),
                decorationBox = { inner ->
                    if (note.isEmpty()) {
                        Text(
                            "Qu'est-ce qui se passe en ce moment…",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Ink3),
                        )
                    }
                    inner()
                },
            )
        }

        Spacer(Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SoftButton(
                onClick = onSkip,
                text = "Passer",
                modifier = Modifier.width(120.dp),
            )
            FilledButton(
                onClick = onSend,
                text = "Envoyer →",
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(26.dp))
    }
}
