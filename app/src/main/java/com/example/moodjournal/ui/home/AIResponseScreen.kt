package com.example.moodjournal.ui.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.components.LumiMood
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.components.SoftButton
import com.example.moodjournal.ui.components.emotionById
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.ui.theme.TerracottaBg
import com.example.moodjournal.viewmodel.MoodJournalViewModel

private val cardShape = RoundedCornerShape(22.dp)

@Composable
fun AIResponseScreen(
    viewModel: MoodJournalViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.generateCheckinResponse() }

    if (viewModel.checkinLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Lumi(size = 100.dp, mood = LumiMood.THINKING)
                Spacer(Modifier.height(24.dp))
                CircularProgressIndicator(color = Terracotta, strokeWidth = 2.dp)
                Spacer(Modifier.height(16.dp))
                Text("Lumi te prépare une réponse…", style = MaterialTheme.typography.bodyMedium, color = Ink2)
            }
        }
        return
    }

    val emotion = emotionById(viewModel.checkinEmotion)
    val lumiMood = when (viewModel.checkinEmotion) {
        "anxious", "overwhelmed", "sad" -> LumiMood.CONCERNED
        "joy", "grateful"               -> LumiMood.HAPPY
        else                            -> LumiMood.CALM
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ScreenHeader(onBack = onNext, eyebrow = "RÉPONSE DE LUMI")

        Column(modifier = Modifier.padding(horizontal = 22.dp)) {

            // Bulle utilisateur
            if (emotion != null) {
                Box(modifier = Modifier.align(Alignment.End)) {
                    Row(
                        modifier = Modifier
                            .background(emotion.bgColor, RoundedCornerShape(18.dp, 4.dp, 18.dp, 18.dp))
                            .padding(10.dp, 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(emotion.emoji, style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp))
                        Column {
                            Text(
                                "${emotion.label} · ${viewModel.checkinIntensity}%",
                                style = MaterialTheme.typography.labelMedium,
                                color = Ink,
                            )
                            if (viewModel.checkinNote.isNotEmpty()) {
                                Spacer(Modifier.height(2.dp))
                                Text(viewModel.checkinNote, style = MaterialTheme.typography.bodyMedium, color = Ink2)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // Message Lumi
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Lumi(size = 36.dp, mood = lumiMood)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Cream2, RoundedCornerShape(4.dp, 18.dp, 18.dp, 18.dp))
                        .padding(14.dp, 12.dp),
                ) {
                    val erreur = viewModel.checkinError
                    if (erreur != null) {
                        Column {
                            Text(erreur, style = MaterialTheme.typography.bodyLarge, color = Ink2)
                            Spacer(Modifier.height(10.dp))
                            SoftButton(onClick = { viewModel.retryCheckinResponse() }, text = "Réessayer")
                        }
                    } else {
                        Text(
                            viewModel.aiCheckinResponse,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Ink,
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Citation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TerracottaBg, cardShape)
                    .padding(18.dp),
            ) {
                Column {
                    Text("\"", style = MaterialTheme.typography.displayLarge.copy(fontSize = 38.sp), color = Terracotta.copy(alpha = 0.4f))
                    Text(
                        "Tu n'as pas besoin de tout résoudre aujourd'hui.\nJuste de remarquer ce qui est là.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink,
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            FilledButtonBlock(onClick = onNext, text = "Continuer →")

            Spacer(Modifier.height(30.dp))
        }
    }
}
