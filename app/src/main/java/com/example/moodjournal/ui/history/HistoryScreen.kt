package com.example.moodjournal.ui.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun HistoryScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize())
}
