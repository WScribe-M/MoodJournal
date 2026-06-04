package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.components.GhostButton
import com.example.moodjournal.ui.components.MoodTextField
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun SignupScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
) {
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailTouched by remember { mutableStateOf(false) }
    val emailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val sexes = listOf("Femme", "Homme", "Autre")
    var sexe by remember { mutableStateOf("") }
    val isValid = nom.isNotEmpty() && prenom.isNotEmpty() && emailValid

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(eyebrow = "02 / 05")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            Text(
                "Comment tu\nt'appelles ?",
                style = MaterialTheme.typography.titleLarge,
                color = Ink,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Ces informations restent sur ton appareil.",
                style = MaterialTheme.typography.bodyMedium,
                color = Ink2,
            )

            Spacer(Modifier.height(28.dp))

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                MoodTextField(
                    value = prenom,
                    onValueChange = { prenom = it },
                    label = "Prénom",
                    placeholder = "Emma",
                )
                MoodTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = "Nom",
                    placeholder = "Dupont",
                )
                Column {
                    MoodTextField(
                        value = email,
                        onValueChange = { email = it; emailTouched = true },
                        label = "Adresse e-mail",
                        placeholder = "emma@exemple.com",
                    )
                    if (emailTouched && email.isNotEmpty() && !emailValid) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Format invalide — ex. emma@exemple.com",
                            style = MaterialTheme.typography.bodySmall,
                            color = Blush,
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                "GENRE",
                style = MaterialTheme.typography.labelSmall,
                color = Ink2,
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                sexes.forEach { s ->
                    GhostButton(
                        onClick = { sexe = s },
                        text = s,
                        modifier = Modifier.weight(1f),
                        isSelected = sexe == s,
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Text(
                "🔒 Tes données ne quittent jamais ton appareil.",
                style = MaterialTheme.typography.bodySmall,
                color = Ink3,
            )

            Spacer(Modifier.height(14.dp))

            FilledButtonBlock(
                onClick = {
                    viewModel.saveUser(nom, prenom, email, sexe)
                    onNext()
                },
                enabled = isValid,
                text = "Continuer →",
            )

            Spacer(Modifier.height(26.dp))
        }
    }
}
