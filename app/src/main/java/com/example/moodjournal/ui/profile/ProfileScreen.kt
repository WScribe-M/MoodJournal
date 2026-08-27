package com.example.moodjournal.ui.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.components.LumiMood
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.theme.BlushBg
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.viewmodel.MoodJournalViewModel

private val cardShape = RoundedCornerShape(22.dp)

@Composable
fun ProfileScreen(
    viewModel: MoodJournalViewModel,
    onLogout: () -> Unit,
) {
    val context = LocalContext.current
    val user    = viewModel.getUser()

    var showDeleteDialog by remember { mutableStateOf(false) }

    // Dialog confirmation suppression
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = Paper,
            title = {
                Text("Supprimer toutes mes données ?", style = MaterialTheme.typography.titleMedium, color = Ink)
            },
            text = {
                Text(
                    "Check-ins, gratitude et profil seront effacés définitivement. Cette action est irréversible.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Ink2,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearUser()
                    showDeleteDialog = false
                    onLogout()
                }) {
                    Text("Supprimer", color = Blush)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Annuler", color = Ink2)
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp)) {
            Text("Mon profil", style = MaterialTheme.typography.titleLarge, color = Ink)
        }

        // Carte utilisateur
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .background(Paper, cardShape)
                .border(1.dp, Line, cardShape)
                .padding(18.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Lumi(size = 56.dp, mood = LumiMood.CALM)
                Column {
                    Text(
                        if (user != null) "${user.prenom} ${user.nom}" else "Invité",
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink,
                    )
                    if (!user?.email.isNullOrEmpty()) {
                        Text(user!!.email, style = MaterialTheme.typography.bodySmall, color = Ink3)
                    }
                    if (!user?.objectifs.isNullOrEmpty()) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            user!!.objectifs!!.take(2).joinToString(" · "),
                            style = MaterialTheme.typography.bodySmall,
                            color = Ink2,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        // Section données
        SectionLabel("DONNÉES")

        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .background(Paper, cardShape)
                .border(1.dp, Line, cardShape),
        ) {
            Column {
                SettingRow(
                    label = "Exporter mes données",
                    color = Ink,
                    onClick = {
                        val text = buildString {
                            appendLine("=== MoodJournal — Profil ===")
                            if (user != null) {
                                appendLine("Prénom : ${user.prenom}")
                                appendLine("Nom    : ${user.nom}")
                                appendLine("Email  : ${user.email}")
                                if (!user.objectifs.isNullOrEmpty()) {
                                    appendLine("Objectifs : ${user.objectifs.joinToString(", ")}")
                                }
                            }
                        }
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, text)
                            putExtra(Intent.EXTRA_SUBJECT, "Mon profil Lumi")
                        }
                        context.startActivity(Intent.createChooser(intent, "Exporter via…"))
                    },
                )
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Line))
                SettingRow(
                    label = "Supprimer mes données",
                    color = Blush,
                    onClick = { showDeleteDialog = true },
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        // Section à propos
        SectionLabel("À PROPOS")

        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .background(Paper, cardShape)
                .border(1.dp, Line, cardShape),
        ) {
            Column {
                SettingRow(label = "Confidentialité", color = Ink)
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Line))
                SettingRow(label = "Conditions d'utilisation", color = Ink)
            }
        }

        Spacer(Modifier.height(30.dp))
    }
}

@Composable
private fun SectionLabel(label: String) {
    Text(
        label,
        style = MaterialTheme.typography.labelSmall,
        color = Ink3,
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp),
    )
}

@Composable
private fun SettingRow(
    label: String,
    color: Color,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, color = color)
        Text("›", style = MaterialTheme.typography.titleMedium, color = Ink3)
    }
}
