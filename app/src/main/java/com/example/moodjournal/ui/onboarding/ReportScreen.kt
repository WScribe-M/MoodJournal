package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun ReportScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
){
    // Unit signifie "lance ça une seule fois au chargement de l'écran".
    LaunchedEffect(Unit) {
        viewModel.generateReport()
    }

    Column() {
        if (viewModel.aiReport.isNotEmpty()) {
            Text(viewModel.aiReport)
            Button(onClick = onNext) {
                Text("Continuer")
            }
        } else {
            CircularProgressIndicator()
        }
    }
}