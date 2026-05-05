package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import kotlin.collections.mutableListOf

@Composable
fun GoalsScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
){
    // Liste des objectifs sélectionnés
    var objectifs by remember { mutableStateOf(mutableListOf<String>()) }
    val allObjectifs = listOf(
        "Réduire mon anxiété",
        "Mieux dormir","Gérer mon stress",
        "Gagner en clarté",
        "Stabiliser mon humeur",
        "Me connaître mieux"
    )

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
    ) {
        Text("Qu'est-ce qui t'amène ici ?", style = MaterialTheme.typography.headlineLarge)
        LazyColumn(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(allObjectifs) {
                FilterChip(
                    selected = objectifs.contains(it),
                    onClick = {
                        if (objectifs.contains(it)) {
                            objectifs = (objectifs - it).toMutableList()
                        } else {
                            objectifs = (objectifs + it).toMutableList()
                        }
                        viewModel.updateObjectifs(objectifs)
                    },
                    label = { Text(it) }
                )
            }
        }
        if (objectifs.isNotEmpty()) {
            Button(onClick = onNext) {
                Text("Continuer")
            }
        }
    }

}