package com.example.moodjournal.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.components.LumiMood
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.theme.BlushBg
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
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.ui.theme.TerracottaBg
import com.example.moodjournal.viewmodel.MoodJournalViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val cardShape = RoundedCornerShape(22.dp)

@Composable
fun HomeScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onTool: (String) -> Unit = {},
) {
    val today  = LocalDate.now()
    val dayName = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH)
        .replaceFirstChar { it.uppercase() }
    val prenom = viewModel.getUser()?.prenom ?: "toi"

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // ── En-tête
        Column(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
        ) {
            Text(dayName.uppercase(), style = MaterialTheme.typography.labelSmall, color = Ink3)
            Spacer(Modifier.height(2.dp))
            Text(
                "Bonjour,\n$prenom",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp),
                color = Ink,
            )
        }

        // ── Carte check-in Lumi
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        listOf(Terracotta, Color(0xFFA84A2C))
                    ),
                    shape = cardShape,
                )
                .padding(20.dp),
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Lumi(size = 56.dp, mood = LumiMood.HAPPY)
                    Column {
                        Text("Lumi", style = MaterialTheme.typography.labelMedium, color = Paper.copy(alpha = 0.9f))
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "Comment te sens-tu\naujourd'hui ?",
                            style = MaterialTheme.typography.titleMedium,
                            color = Paper,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Ça ne prend que 2 minutes.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Paper.copy(alpha = 0.75f),
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .background(Paper.copy(alpha = 0.95f), CircleShape)
                        .clickable { onNext() },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Faire mon check-in →",
                        style = MaterialTheme.typography.labelLarge,
                        color = Terracotta,
                    )
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        // ── Outils du moment
        Text(
            "OUTILS DU MOMENT",
            style = MaterialTheme.typography.labelSmall,
            color = Ink3,
            modifier = Modifier.padding(horizontal = 22.dp),
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item { ToolCard("Respiration 4-7-8", "3 min", Icons.Default.Air,           SkyBg,      Sky)     { onTool("breathing") } }
            item { ToolCard("5 choses positives", "4 min", Icons.Default.FavoriteBorder, BlushBg,    Blush)   { onTool("gratitude") } }
            item { ToolCard("Matrice Eisenhower", "5 min", Icons.Default.GridView,       SageBg,     Sage)    { onTool("eisenhower") } }
        }

        Spacer(Modifier.height(22.dp))

        // ── Note du jour Lumi
        Text(
            "NOTE DU JOUR",
            style = MaterialTheme.typography.labelSmall,
            color = Ink3,
            modifier = Modifier.padding(horizontal = 22.dp),
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .background(Paper, cardShape)
                .border(1.dp, Line, cardShape)
                .padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Lumi(size = 40.dp, mood = LumiMood.CALM)
                Text(
                    "Chaque émotion que tu nommes, c'est une victoire. " +
                            "Tu n'as pas besoin de tout résoudre aujourd'hui.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Ink,
                )
            }
        }

        Spacer(Modifier.height(26.dp))
    }
}

@Composable
private fun ToolCard(
    title: String,
    duration: String,
    icon: ImageVector,
    bgColor: Color,
    inkColor: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(158.dp)
            .background(Paper, RoundedCornerShape(18.dp))
            .border(1.dp, Line, RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(bgColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = inkColor, modifier = Modifier.size(18.dp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = Ink)
            Text(duration, style = MaterialTheme.typography.bodySmall, color = Ink3)
        }
    }
}
