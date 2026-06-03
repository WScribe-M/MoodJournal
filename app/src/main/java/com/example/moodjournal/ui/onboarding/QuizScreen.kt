package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import com.example.moodjournal.ui.components.FilledButton
import com.example.moodjournal.ui.components.GhostButton
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun QuizScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val allQuestions = listOf(
        "Tu te réveilles souvent la nuit et as du mal à te rendormir.",
        "Tu te sens triste, vide ou désespéré·e.",
        "Tu as des inquiétudes que tu n'arrives pas à arrêter.",
        "Tu te sens fatigué·e ou avec peu d'énergie.",
        "Tu prends moins de plaisir aux choses que d'habitude.",
        "Tu te sens tendu·e ou sur les nerfs.",
        "Tu as du mal à te concentrer (lecture, conversation, écrans)."
    )
    val allReponses = listOf(
        "Jamais",
        "Quelques jours",
        "Plus de la moitié",
        "Presque tous les jours"
    )
    var reponses by remember { mutableStateOf(MutableList(7) { -1 }) }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp)
    ) {
        Text(allQuestions[currentIndex])
        LazyColumn(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(allReponses) { index, reponse ->
                GhostButton(
                    onClick = {
                        val newReponses = reponses.toMutableList()
                        newReponses[currentIndex] = index
                        reponses = newReponses
                        viewModel.updateReponses(reponses)
                    },
                    text = reponse,
                    modifier = Modifier.fillMaxWidth(),
                    isSelected = reponses.getOrNull(currentIndex) == index
                )
            }

        }
        FilledButton(onClick = {
            if (currentIndex < allQuestions.size - 1) {
                currentIndex++
            } else {
                onNext()
            }
        },
            text = "Suivant"
        )

        GhostButton(onClick = {
            if (currentIndex == 0) {
                onBack()
            } else {
                currentIndex--
            }
        },
            text = "Retour",
            modifier = Modifier,
            isSelected = false
        )
    }

}