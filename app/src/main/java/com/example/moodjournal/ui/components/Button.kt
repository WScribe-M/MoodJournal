package com.example.moodjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.theme.Cream2
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Terracotta

private val WarmWhite = Color(0xFFFFF8EE)

@Composable
fun FilledButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier.height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Terracotta,
            contentColor = WarmWhite,
            disabledContainerColor = Terracotta.copy(alpha = 0.4f),
            disabledContentColor = WarmWhite.copy(alpha = 0.6f),
        ),
        enabled = enabled,
    ) {
        Text(text)
    }
}

@Composable
fun FilledButtonBlock(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
) {
    FilledButton(
        onClick = onClick,
        text = text,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun SoftButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier.height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Cream2,
            contentColor = Ink,
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
    ) {
        Text(text)
    }
}

@Composable
fun GhostButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    isSelected: Boolean,
) {
    OutlinedButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier.fillMaxWidth().height(52.dp),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) Terracotta else Line,
        ),
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(containerColor = Terracotta, contentColor = WarmWhite)
        } else {
            ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Ink2)
        },
    ) {
        Text(text)
    }
}
