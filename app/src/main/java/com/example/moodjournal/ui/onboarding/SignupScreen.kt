package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import kotlinx.coroutines.selects.select

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
        Text("Créer un compte", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = prenom,
            onValueChange = { prenom = it },
            label = { Text("Prenom") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Adresse mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
        Button(onClick = {
            viewModel.saveUser(nom, prenom, email, sexe)
            onNext()
        },
            enabled = isValid
        ) {
            Text("Continuer")
        }
    }
}