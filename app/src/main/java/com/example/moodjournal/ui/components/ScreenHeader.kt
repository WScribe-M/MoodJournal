package com.example.moodjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3

@Composable
fun ScreenHeader(
    onBack: (() -> Unit)? = null,
    eyebrow: String? = null,
    endContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBack != null) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(Cream2, CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Ink2,
                    modifier = Modifier.size(18.dp),
                )
            }
        } else {
            Spacer(modifier = Modifier.size(38.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        if (eyebrow != null) {
            Text(
                eyebrow.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = Ink3,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (endContent != null) {
            Box(modifier = Modifier.wrapContentSize()) { endContent() }
        } else {
            Spacer(modifier = Modifier.size(38.dp))
        }
    }
}
