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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.components.FilledButton
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.components.SoftButton
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.ui.theme.TerracottaBg
import com.example.moodjournal.viewmodel.MoodJournalViewModel

private val QUESTIONS = listOf(
    "Tu te réveilles souvent la nuit et as du mal à te rendormir.",
    "Tu te sens triste, vide ou désespéré·e.",
    "Tu as des inquiétudes que tu n'arrives pas à arrêter.",
    "Tu te sens fatigué·e ou avec peu d'énergie.",
    "Tu prends moins de plaisir aux choses que d'habitude.",
    "Tu te sens tendu·e ou sur les nerfs.",
    "Tu as du mal à te concentrer (lecture, conversation, écrans).",
)

private val RESPONSES = listOf("Jamais", "Quelques jours", "Plus de la moitié", "Presque tous les jours")

@Composable
fun QuizScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    var currentIndex by remember { mutableStateOf(0) }
    var reponses by remember { mutableStateOf(MutableList(QUESTIONS.size) { -1 }) }

    val hasAnswer = reponses.getOrNull(currentIndex) != -1

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            onBack = { if (currentIndex == 0) onBack() else currentIndex-- },
            endContent = {
                Text(
                    "${currentIndex + 1} / ${QUESTIONS.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Ink3,
                )
            },
        )

        // Barre de progression
        Row(
            modifier = Modifier.padding(horizontal = 26.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            QUESTIONS.forEachIndexed { i, _ ->
                val active = i == currentIndex
                val done   = i < currentIndex
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(if (active) 32.dp else 22.dp)
                        .background(
                            color = when {
                                active -> Terracotta
                                done   -> Terracotta.copy(alpha = 0.45f)
                                else   -> Color(0xFFE0D2B9)
                            },
                            shape = RoundedCornerShape(2.dp),
                        ),
                )
            }
        }

        Spacer(Modifier.height(26.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
        ) {
            Text(
                "SOUTIEN · QUESTION ${currentIndex + 1}",
                style = MaterialTheme.typography.labelSmall,
                color = Ink3,
            )

            Spacer(Modifier.height(10.dp))

            Text(
                QUESTIONS[currentIndex],
                style = MaterialTheme.typography.headlineMedium,
                color = Ink,
            )

            Spacer(Modifier.height(28.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                RESPONSES.forEachIndexed { index, label ->
                    val selected = reponses.getOrNull(currentIndex) == index
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selected) TerracottaBg else Paper,
                                RoundedCornerShape(16.dp),
                            )
                            .border(
                                width = if (selected) 1.5.dp else 1.dp,
                                color = if (selected) Terracotta else Line,
                                shape = RoundedCornerShape(16.dp),
                            )
                            .clickable {
                                val updated = reponses.toMutableList()
                                updated[currentIndex] = index
                                reponses = updated
                                viewModel.updateReponses(reponses)
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Radio circle
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .background(
                                    if (selected) Terracotta else Color.Transparent,
                                    CircleShape,
                                )
                                .border(
                                    1.5.dp,
                                    if (selected) Terracotta else Ink3,
                                    CircleShape,
                                ),
                        )
                        Text(label, style = MaterialTheme.typography.bodyLarge, color = Ink)
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftButton(
                    onClick = { if (currentIndex == 0) onBack() else currentIndex-- },
                    text = "← Retour",
                    modifier = Modifier.width(130.dp),
                )
                FilledButton(
                    onClick = {
                        if (currentIndex < QUESTIONS.size - 1) currentIndex++
                        else onNext()
                    },
                    text = if (currentIndex == QUESTIONS.size - 1) "Terminer →" else "Suivant →",
                    enabled = hasAnswer,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(Modifier.height(26.dp))
        }
    }
}
