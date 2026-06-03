package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.FilterChip
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
import com.example.moodjournal.ui.components.FilledButton
import com.example.moodjournal.ui.components.GoalCard
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import kotlin.collections.mutableListOf

@Composable
fun GoalsScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
){
    // Liste des objectifs sélectionnés
    var objectifs by remember { mutableStateOf(mutableListOf<Objectif>(
    )) }
    val allObjectifs = listOf(
        Objectif
        (
            titre = "Réduire mon anxiété",
            description = "Apaiser les pensées qui tournent",
            icone = Icons.Default.Air
        ),
        Objectif
        (
            titre = "Mieux dormir",
            description = " Trouver un sommeil plus profond",
            icone = Icons.Default.Bedtime
        ),
        Objectif
        (
            titre = "Gérer mon stress",
            description = "Tenir les périodes intenses",
            icone = Icons.Default.WaterDrop
        ),
        Objectif
        (
            titre = "Gagner en clarté",
            description = "Moins d'éparpillement, plus d'action",
            icone = Icons.Default.Lightbulb
        ),
        Objectif
        (
            titre = "Stabiliser mon humeur",
            description = "Moins de hauts et bas",
            icone = Icons.Default.Lightbulb
        ),
        Objectif
        (
            titre = "Me connaître mieux",
            description = "Comprendre mes émotions",
            icone = Icons.Default.SelfImprovement
        )

    )

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
    ) {
        Text("Qu'est-ce qui t'amène ici ?", style = MaterialTheme.typography.headlineLarge)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(allObjectifs) {
                GoalCard(
                    isSelected = objectifs.contains(it),
                    onClick = {
                        if (objectifs.contains(it)) {
                            objectifs = (objectifs - it).toMutableList()
                        } else {
                            objectifs = (objectifs + it).toMutableList()
                        }
                    },
                    objectif = it
                )
            }
        }
        if (objectifs.isNotEmpty()) {
            FilledButton(onClick = {
                viewModel.updateUserObjectifs(objectifs.map { it.titre })
                onNext()
            },
                text = "Continuer"
            )
        }
    }

}