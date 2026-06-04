package com.example.moodjournal.ui.tools

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.ScreenHeader
import com.example.moodjournal.ui.components.SoftButton
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Sky
import com.example.moodjournal.ui.theme.SkyBg
import kotlinx.coroutines.delay

private enum class BreathPhase { IDLE, IN, HOLD, OUT, DONE }

@Composable
fun BreathingScreen(onClose: () -> Unit) {
    var running by remember { mutableStateOf(false) }
    var phase   by remember { mutableStateOf(BreathPhase.IDLE) }
    var count   by remember { mutableIntStateOf(0) }
    var cycle   by remember { mutableIntStateOf(0) }
    val totalCycles = 4

    LaunchedEffect(running) {
        if (!running) return@LaunchedEffect
        var p = BreathPhase.IN
        var c = 4
        var cy = 0
        phase = p; count = c; cycle = cy

        while (true) {
            delay(1000)
            c--
            if (c <= 0) {
                p = when (p) {
                    BreathPhase.IN   -> BreathPhase.HOLD.also { c = 7 }
                    BreathPhase.HOLD -> BreathPhase.OUT.also  { c = 8 }
                    else             -> {
                        cy++
                        if (cy >= totalCycles) {
                            phase = BreathPhase.DONE
                            running = false
                            return@LaunchedEffect
                        }
                        BreathPhase.IN.also { c = 4 }
                    }
                }
            }
            phase = p; count = c; cycle = cy
        }
    }

    // Taille orb selon la phase
    val orbTargetDp = when (phase) {
        BreathPhase.IN   -> 220.dp
        BreathPhase.OUT  -> 120.dp
        BreathPhase.DONE -> 180.dp
        else             -> 160.dp
    }
    val orbDurationMs = when (phase) {
        BreathPhase.IN  -> 4000
        BreathPhase.OUT -> 8000
        else            -> 400
    }
    val orbSize by animateDpAsState(
        targetValue = orbTargetDp,
        animationSpec = tween(orbDurationMs),
        label = "orb",
    )

    val phaseLabel = when (phase) {
        BreathPhase.IDLE -> "Respiration 4-7-8"
        BreathPhase.IN   -> "Inspire"
        BreathPhase.HOLD -> "Retiens"
        BreathPhase.OUT  -> "Expire"
        BreathPhase.DONE -> "Bien joué !"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(SkyBg, MaterialTheme.colorScheme.background))),
    ) {
        ScreenHeader(onBack = onClose, eyebrow = "RESPIRATION 4-7-8")

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                // Orb
                Box(
                    modifier = Modifier
                        .size(orbSize)
                        .background(Brush.radialGradient(listOf(SkyBg, Sky)), CircleShape)
                        .border(3.dp, Sky.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            phaseLabel,
                            style = MaterialTheme.typography.titleMedium,
                            color = Ink,
                            textAlign = TextAlign.Center,
                        )
                        if (phase != BreathPhase.IDLE && phase != BreathPhase.DONE) {
                            Text(
                                "$count",
                                style = MaterialTheme.typography.displayLarge.copy(fontSize = 56.sp),
                                color = Ink,
                            )
                        }
                    }
                }

                when (phase) {
                    BreathPhase.IDLE -> Text(
                        "Inspire 4 s · retiens 7 s · expire 8 s\nQuatre cycles.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Ink2,
                        textAlign = TextAlign.Center,
                    )
                    BreathPhase.DONE -> Text(
                        "Exercice terminé — bien joué !",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Ink2,
                        textAlign = TextAlign.Center,
                    )
                    else -> Text(
                        "Cycle ${cycle + 1} / $totalCycles",
                        style = MaterialTheme.typography.bodySmall,
                        color = Ink3,
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 26.dp, vertical = 18.dp)) {
            when {
                phase == BreathPhase.DONE -> FilledButtonBlock(onClick = onClose, text = "Terminer")
                !running -> FilledButtonBlock(
                    onClick = { running = true },
                    text = "Commencer →",
                )
                else -> SoftButton(
                    onClick = { running = false; phase = BreathPhase.IDLE },
                    text = "Pause",
                    modifier = Modifier,
                )
            }
        }
    }
}
