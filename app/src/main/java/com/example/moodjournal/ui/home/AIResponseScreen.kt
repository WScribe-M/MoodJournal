package com.example.moodjournal.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun AIResponseScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
) {
    // Unit signifie "lance ça une seule fois au chargement de l'écran".
    LaunchedEffect(Unit) {
        viewModel.generateCheckinResponse()
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
    ) {
        if (viewModel.aiCheckinResponse.isNotEmpty()) {
            Text(viewModel.aiCheckinResponse)
            Button(onClick = onNext) {
                Text("Continuer")
            }
        } else {
            CircularProgressIndicator()
        }
    }
}