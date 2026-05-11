package com.example.moodjournal.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import java.time.LocalDate

@Composable
fun GratitudeScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    var gratitudeResponse by remember { mutableStateOf(List(5) { "" }) }
    val today = LocalDate.now()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text("Cinq choses positives", style = MaterialTheme.typography.titleMedium)

        gratitudeResponse.forEachIndexed { index, note ->
            Card {
                Row {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7BA098)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${index + 1}")
                    }

                    OutlinedTextField(
                        value = note,
                        onValueChange = { newValue ->
                            gratitudeResponse = gratitudeResponse.mapIndexed { i, v ->
                                if (i == index) newValue else v
                            }
                        }
                    )
                }
            }
        }
        if (gratitudeResponse.any { it.isNotEmpty() }){
            Button(
                onClick = {
                    viewModel.saveGratitude(gratitudeResponse)
                    onClose()
                },
            ) {
                Text("Enregistrer")
            }
        }
    }

}