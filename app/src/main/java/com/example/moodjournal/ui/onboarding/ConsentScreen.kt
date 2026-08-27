package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun ConsentScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    var consentProfil by remember { mutableStateOf(false) }
    var consentIA by remember { mutableStateOf(false) }
    val isValid = consentProfil && consentIA

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(onBack = onBack, eyebrow = "01 / 05")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    "Avant de\ncommencer",
                    style = MaterialTheme.typography.titleLarge,
                    color = Ink,
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Lumi a besoin de ton accord pour deux choses. Tu peux retirer ton consentement à tout moment en supprimant tes données depuis ton profil.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Ink2,
                )

                Spacer(Modifier.height(24.dp))

                ConsentItem(
                    checked = consentProfil,
                    onCheckedChange = { consentProfil = it },
                    title = "Enregistrer mon profil sur cet appareil",
                    detail = "Ton prénom, ton nom, ton e-mail et ton genre sont stockés uniquement en local, sur ton téléphone. Ils ne sont envoyés à aucun serveur.",
                )

                Spacer(Modifier.height(14.dp))

                ConsentItem(
                    checked = consentIA,
                    onCheckedChange = { consentIA = it },
                    title = "Traitement par une IA tierce",
                    detail = "Pour générer les réponses de Lumi, tes objectifs et tes check-ins (émotion, intensité, note libre) sont envoyés à un service d'IA tiers (Hugging Face / Meta Llama). Aucun nom ni e-mail n'est transmis. Évite d'écrire des informations personnelles dans la note libre.",
                )

                Spacer(Modifier.height(20.dp))
            }

            Text(
                "En continuant, tu confirmes avoir au moins 16 ans et avoir lu ces informations.",
                style = MaterialTheme.typography.bodySmall,
                color = Ink3,
            )

            Spacer(Modifier.height(14.dp))

            FilledButtonBlock(
                onClick = {
                    viewModel.setConsentGiven(true)
                    onNext()
                },
                enabled = isValid,
                text = "J'accepte et je continue →",
            )

            Spacer(Modifier.height(26.dp))
        }
    }
}

@Composable
private fun ConsentItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    detail: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Paper, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Terracotta,
                uncheckedColor = Ink3,
                checkmarkColor = Cream2,
            ),
        )
        Column(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = Ink,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                detail,
                style = MaterialTheme.typography.bodyMedium,
                color = Ink2,
            )
        }
    }
}
