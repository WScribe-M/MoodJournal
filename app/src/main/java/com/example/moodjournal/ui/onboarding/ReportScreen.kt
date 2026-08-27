package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.components.LumiMood
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.theme.BlushBg
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
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

private val cardShape = RoundedCornerShape(22.dp)

@Composable
fun ReportScreen(
    viewModel: MoodJournalViewModel,
    onNext: () -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.generateReport() }

    val user = viewModel.getUser()
    val prenom = user?.prenom ?: ""

    if (viewModel.aiReport.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Lumi(size = 100.dp, mood = LumiMood.THINKING)
                Spacer(Modifier.height(24.dp))
                CircularProgressIndicator(color = Terracotta, strokeWidth = 2.dp)
                Spacer(Modifier.height(16.dp))
                Text("Lumi analyse tes réponses…", style = MaterialTheme.typography.bodyMedium, color = Ink2)
            }
        }
        return
    }

    // Calcul du score depuis les réponses quiz (0-3 par question)
    val reponses = viewModel.reponses
    val maxScore = 7 * 3
    val rawScore = reponses.take(7).sumOf { it.coerceAtLeast(0) }
    val wellbeingScore = (100 - (rawScore.toFloat() / maxScore * 100)).toInt().coerceIn(0, 100)

    // Émotions dominantes calculées des réponses
    val tiredScore   = (reponses.getOrElse(0) { 0 } + reponses.getOrElse(3) { 0 }) / 2f
    val sadScore     = (reponses.getOrElse(1) { 0 } + reponses.getOrElse(4) { 0 }) / 2f
    val anxiousScore = (reponses.getOrElse(2) { 0 } + reponses.getOrElse(5) { 0 }) / 2f
    val overwhelmed  = reponses.getOrElse(6) { 0 }.toFloat()

    data class EmoStat(val label: String, val emoji: String, val color: Color, val pct: Int)
    val emos = listOf(
        EmoStat("Anxieux",   "🌪", Blush,    (anxiousScore / 3f * 100).toInt()),
        EmoStat("Fatigué",   "🌙", Sky,      (tiredScore   / 3f * 100).toInt()),
        EmoStat("Submergé",  "🌊", Blush,    (overwhelmed  / 3f * 100).toInt()),
        EmoStat("Triste",    "🌧", Sky,      (sadScore     / 3f * 100).toInt()),
    ).filter { it.pct > 0 }.sortedByDescending { it.pct }.take(3)

    val scoreLabel = when {
        wellbeingScore >= 70 -> "Tu vas plutôt bien — continue comme ça."
        wellbeingScore >= 45 -> "À surveiller — tu n'es pas seul·e."
        else                 -> "Période difficile — on est là pour toi."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ScreenHeader(eyebrow = "BILAN INITIAL · 05 / 05")

        Column(modifier = Modifier.padding(horizontal = 22.dp)) {
            Text(
                if (prenom.isNotEmpty()) "Voici ce que je ressens,\n$prenom." else "Voici ce que je ressens.",
                style = MaterialTheme.typography.titleLarge,
                color = Ink,
            )

            Spacer(Modifier.height(22.dp))

            // Carte score
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Paper, cardShape)
                    .padding(18.dp),
            ) {
                Column {
                    Text("INDICE DE BIEN-ÊTRE", style = MaterialTheme.typography.labelSmall, color = Ink3)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            "$wellbeingScore",
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 56.sp),
                            color = Terracotta,
                        )
                        Text(
                            " /100",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Ink3,
                        )
                    }
                    Text(scoreLabel, style = MaterialTheme.typography.bodyMedium, color = Ink2)
                    Spacer(Modifier.height(14.dp))
                    // Jauge
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Cream2, RoundedCornerShape(4.dp)),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(wellbeingScore / 100f)
                                .height(8.dp)
                                .background(
                                    androidx.compose.ui.graphics.Brush.horizontalGradient(
                                        listOf(Blush, Terracotta, Marigold)
                                    ),
                                    RoundedCornerShape(4.dp),
                                ),
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // Carte émotions
            if (emos.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Paper, cardShape)
                        .padding(18.dp),
                ) {
                    Column {
                        Text("ÉMOTIONS DOMINANTES", style = MaterialTheme.typography.labelSmall, color = Ink3)
                        Spacer(Modifier.height(12.dp))
                        emos.forEach { e ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Text(e.emoji, style = MaterialTheme.typography.bodyLarge)
                                Text(e.label, style = MaterialTheme.typography.labelMedium, modifier = Modifier.width(96.dp), color = Ink)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(6.dp)
                                        .background(Cream2, RoundedCornerShape(3.dp)),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(e.pct / 100f)
                                            .height(6.dp)
                                            .background(e.color.copy(alpha = 0.85f), RoundedCornerShape(3.dp)),
                                    )
                                }
                                Text("${e.pct}%", style = MaterialTheme.typography.bodySmall, color = Ink3)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
            }

            // Message Lumi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TerracottaBg, cardShape)
                    .padding(18.dp),
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Lumi(size = 36.dp, mood = LumiMood.CALM)
                        Column {
                            Text("Lumi", style = MaterialTheme.typography.labelMedium, color = Ink)
                            Text("Mon premier message pour toi", style = MaterialTheme.typography.bodySmall, color = Ink2)
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        viewModel.aiReport,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Ink,
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            FilledButtonBlock(onClick = onNext, text = "Découvrir mon espace →")

            Spacer(Modifier.height(30.dp))
        }
    }
}
