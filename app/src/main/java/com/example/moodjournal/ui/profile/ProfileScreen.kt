package com.example.moodjournal.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun ProfileScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    val user = viewModel.getUser()
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
    ) {
        Card() {
            Text("Nom : ${user?.nom}")
            Text("Prénom : ${user?.prenom}")
            Text("Email : ${user?.email}")
            Text("Sexe : ${user?.sexe}")
            Text("Objectifs : ${user?.objectifs?.joinToString()}")
        }
        Button(onClick = {
            viewModel.clearUser()
            onLogout()
        }) {
            Text("Supprimer mes données")
        }
    }
}