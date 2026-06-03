package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.moodjournal.ui.components.FilledButton
import com.example.moodjournal.ui.components.MoodTextField
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun SignupScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit
) {
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val sexes = listOf<String>("Homme", "Femme", "Autre")
    var sexe by remember { mutableStateOf("") }
    val isValid = nom.isNotEmpty() && prenom.isNotEmpty() && email.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créer un compte", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        MoodTextField(
            value = nom,
            onValueChange = { nom = it },
            label = "Nom",
            placeholder = "Nom..."
        )
        MoodTextField(
            value = prenom,
            onValueChange = { prenom = it },
            label = "Prenom",
            placeholder = "Prenom..."
        )
        MoodTextField(
            value = email,
            onValueChange = { email = it },
            label = "Adresse mail",
            placeholder = "Adresse mail..."
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            sexes.forEach {
                FilterChip(
                    selected = sexe == it,
                    onClick = {
                        sexe = it
                    },
                    label = { Text(it) }
                )
            }
        }
        FilledButton(onClick = {
            viewModel.saveUser(nom, prenom, email, sexe)
            onNext()
        },
            enabled = isValid,
            text = "Continuer"
        )
    }
}