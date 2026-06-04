package com.example.moodjournal.ui.onboarding

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.theme.BlushBg
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Marigold
import com.example.moodjournal.ui.theme.MarigoldBg
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Sage
import com.example.moodjournal.ui.theme.SageBg
import com.example.moodjournal.ui.theme.Sky
import com.example.moodjournal.ui.theme.SkyBg

private val cardShape = RoundedCornerShape(18.dp)

data class ToolItem(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val icon: ImageVector,
    val bgColor: Color,
    val inkColor: Color,
    val live: Boolean = true,
)

private val TOOLS_BY_SECTION = listOf(
    "Calmer" to listOf(
        ToolItem("breathing",   "Respiration 4-7-8",      "Technique de cohérence cardiaque",    "3 min", Icons.Default.Air,           SkyBg,       Sky),
        ToolItem("anxiety",     "Ancrage 5-4-3-2-1",       "Reviens dans l'instant présent",       "2 min", Icons.Default.Air,           BlushBg,     Blush,  live = false),
    ),
    "Remarquer" to listOf(
        ToolItem("gratitude",   "5 choses positives",     "Entraîne ton regard sur le bon",       "4 min", Icons.Default.FavoriteBorder, BlushBg,     Blush),
        ToolItem("perf",        "Journal des victoires",  "Note ce que tu as accompli",            "3 min", Icons.Default.FavoriteBorder, MarigoldBg,  Marigold, live = false),
    ),
    "Agir" to listOf(
        ToolItem("eisenhower",  "Matrice d'Eisenhower",   "Trie tes tâches par urgence",           "5 min", Icons.Default.GridView,       SageBg,      Sage),
        ToolItem("smart",       "Objectifs SMART",        "Formule ce que tu veux atteindre",     "5 min", Icons.Default.GridView,       MarigoldBg,  Marigold, live = false),
    ),
)

@Composable
fun ToolsScreen(onTool: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp)) {
            Text("Outils bien-être", style = MaterialTheme.typography.titleLarge, color = Ink)
            Spacer(Modifier.height(4.dp))
            Text("Des pratiques courtes pour te recentrer.", style = MaterialTheme.typography.bodyMedium, color = Ink2)
        }

        TOOLS_BY_SECTION.forEach { (section, tools) ->
            Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp)) {
                Text(section.uppercase(), style = MaterialTheme.typography.labelSmall, color = Ink3)
                Spacer(Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    tools.forEach { tool ->
                        ToolRow(tool = tool, onClick = { if (tool.live) onTool(tool.id) })
                    }
                }
            }
        }

        Spacer(Modifier.height(18.dp))
    }
}

@Composable
private fun ToolRow(tool: ToolItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (tool.live) 1f else 0.6f)
            .background(Paper, cardShape)
            .border(1.dp, Line, cardShape)
            .clickable(enabled = tool.live) { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(tool.bgColor, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(tool.icon, contentDescription = null, tint = tool.inkColor, modifier = Modifier.size(20.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(tool.title, style = MaterialTheme.typography.labelLarge, color = Ink)
            Text(tool.description, style = MaterialTheme.typography.bodySmall, color = Ink3)
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            Text(tool.duration, style = MaterialTheme.typography.bodySmall, color = Ink2)
        }
    }
}
