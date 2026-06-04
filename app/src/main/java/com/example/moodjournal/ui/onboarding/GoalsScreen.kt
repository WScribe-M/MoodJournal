package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.data.model.Objectif
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.GoalCard
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun GoalsScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
) {
    var objectifs by remember { mutableStateOf(listOf<Objectif>()) }

    val allObjectifs = listOf(
        Objectif("Réduire mon anxiété",  "Apaiser les pensées qui tournent",      Icons.Default.Air),
        Objectif("Mieux dormir",         "Trouver un sommeil plus profond",        Icons.Default.Bedtime),
        Objectif("Gérer mon stress",     "Tenir les périodes intenses",            Icons.Default.WaterDrop),
        Objectif("Gagner en clarté",     "Moins d'éparpillement, plus d'action",  Icons.Default.Lightbulb),
        Objectif("Stabiliser mon humeur","Moins de hauts et bas",                  Icons.Default.FavoriteBorder),
        Objectif("Me connaître mieux",   "Comprendre mes émotions",               Icons.Default.SelfImprovement),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(eyebrow = "03 / 05")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
        ) {
            Text(
                "Qu'est-ce qui\nt'amène ici ?",
                style = MaterialTheme.typography.titleLarge,
                color = Ink,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Choisis un ou plusieurs objectifs.",
                style = MaterialTheme.typography.bodyMedium,
                color = Ink2,
            )

            Spacer(Modifier.height(22.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(allObjectifs) { obj ->
                    GoalCard(
                        isSelected = objectifs.contains(obj),
                        onClick = {
                            objectifs = if (objectifs.contains(obj)) {
                                objectifs - obj
                            } else {
                                objectifs + obj
                            }
                        },
                        objectif = obj,
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            FilledButtonBlock(
                onClick = {
                    viewModel.updateUserObjectifs(objectifs.map { it.titre })
                    onNext()
                },
                enabled = objectifs.isNotEmpty(),
                text = "Continuer →",
            )

            Spacer(Modifier.height(26.dp))
        }
    }
}
