package com.example.moodjournal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.components.FilledButtonBlock
import com.example.moodjournal.ui.components.Lumi
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.SageBg
import com.example.moodjournal.ui.theme.TerracottaBg

@Composable
fun SplashScreen(onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Blob terracotta — haut droite
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = 140.dp, y = (-60).dp)
                .background(TerracottaBg, CircleShape),
        )
        // Blob sage — bas gauche
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-70).dp, y = 520.dp)
                .background(SageBg, CircleShape),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "MOODJOURNAL",
                style = MaterialTheme.typography.labelSmall,
                color = Ink3,
            )

            Spacer(Modifier.weight(1f))

            Lumi(size = 150.dp)

            Spacer(Modifier.height(24.dp))

            Text(
                "Prenons soin\nde toi",
                style = MaterialTheme.typography.displayLarge,
                color = Ink,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "Un espace calme pour mieux te connaître,\njour après jour.",
                style = MaterialTheme.typography.bodyMedium,
                color = Ink2,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))

            FilledButtonBlock(onClick = onNext, text = "Commencer →")

            Spacer(Modifier.height(28.dp))
        }
    }
}
