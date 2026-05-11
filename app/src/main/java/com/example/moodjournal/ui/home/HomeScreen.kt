package com.example.moodjournal.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.format.TextStyle
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import java.time.LocalDate
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
){
    val today = LocalDate.now()
    val dayName = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH)
    val user = viewModel.getUser()

    Column(modifier = modifier.fillMaxSize().padding(24.dp)) {
        Text(dayName)
        Text("Bonjour, ${user?.prenom ?: "invité"}", style = MaterialTheme.typography.titleMedium)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD2691E))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Lumi", style = MaterialTheme.typography.bodySmall)
                Text(
                    "Comment te sens-tu aujourd'hui ?",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = onNext) {
                    Text("Faire mon check-in")
                }
            }
        }
    }
}