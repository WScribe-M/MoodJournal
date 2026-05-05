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
import java.util.logging.Filter
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Alignment
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun QuizScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var reponses by remember { mutableStateOf(mutableListOf<Int>()) }
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
                FilterChip(
                    selected = false,
                    onClick = {
                        reponses = (reponses + index).toMutableList()
                        if (currentIndex < allQuestions.size - 1) {
                            currentIndex++
                        } else {
                            onNext()
                        }
                        viewModel.updateReponses(reponses)
                    },
                    label = { Text(reponse) }
                )
            }
        }
    }

}