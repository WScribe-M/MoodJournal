package com.example.moodjournal.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta

private val itemShape = RoundedCornerShape(16.dp)

private val PLACEHOLDERS = listOf(
    "Un café qui sentait bon ce matin",
    "Un sourire reçu aujourd'hui",
    "J'ai fini ce que j'avais commencé",
    "Le soleil par la fenêtre",
    "Une chanson qui m'a fait du bien",
)

@Composable
fun GratitudeScreen(onClose: () -> Unit) {
    var items by remember { mutableStateOf(List(5) { "" }) }
    var done  by remember { mutableStateOf(false) }
    val filled = items.count { it.trim().isNotEmpty() }

    if (done) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Lumi(size = 130.dp)
            Spacer(Modifier.height(18.dp))
            Text("Merci de l'avoir remarqué.", style = MaterialTheme.typography.titleLarge, color = Ink, textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text(
                "Ces $filled chose${if (filled > 1) "s" else ""} positive${if (filled > 1) "s" else ""} vont t'accompagner.",
                style = MaterialTheme.typography.bodyMedium,
                color = Ink2,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(28.dp))
            FilledButtonBlock(onClick = onClose, text = "Continuer →")
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(onBack = onClose, eyebrow = "5 CHOSES POSITIVES")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp),
        ) {
            Text("Nomme 5 choses\npositives d'aujourd'hui.", style = MaterialTheme.typography.titleLarge, color = Ink)
            Spacer(Modifier.height(4.dp))
            Text("Même les plus petites comptent.", style = MaterialTheme.typography.bodyMedium, color = Ink2)
            Spacer(Modifier.height(18.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items.forEachIndexed { i, v ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Paper, itemShape)
                            .border(1.dp, Line, itemShape)
                            .padding(10.dp, 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    if (v.trim().isNotEmpty()) Terracotta else Cream2,
                                    CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "${i + 1}",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (v.trim().isNotEmpty()) Paper else Ink3,
                            )
                        }
                        BasicTextField(
                            value = v,
                            onValueChange = { new ->
                                items = items.mapIndexed { idx, old -> if (idx == i) new else old }
                            },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Ink),
                            cursorBrush = SolidColor(Terracotta),
                            modifier = Modifier.weight(1f),
                            decorationBox = { inner ->
                                if (v.isEmpty()) {
                                    Text(PLACEHOLDERS[i], style = MaterialTheme.typography.bodyLarge.copy(color = Ink3))
                                }
                                inner()
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "$filled / 5",
                style = MaterialTheme.typography.bodySmall,
                color = Ink3,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(10.dp))

            FilledButtonBlock(
                onClick = { done = true },
                enabled = filled >= 3,
                text = "Enregistrer ✓",
            )

            Spacer(Modifier.height(26.dp))
        }
    }
}
