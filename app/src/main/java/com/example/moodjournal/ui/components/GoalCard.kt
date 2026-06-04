package com.example.moodjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodjournal.data.model.Objectif
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.ui.theme.TerracottaBg

@Composable
fun GoalCard(onClick: () -> Unit, objectif: Objectif, isSelected: Boolean) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(130.dp),
        shape = RoundedCornerShape(18.dp),
        border = if (isSelected) BorderStroke(1.5.dp, Terracotta) else BorderStroke(1.dp, Line),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) TerracottaBg else Paper),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                imageVector = objectif.icone,
                contentDescription = null,
                tint = if (isSelected) Terracotta else Ink3,
                modifier = Modifier.size(26.dp),
            )
            Text(
                objectif.titre,
                style = MaterialTheme.typography.labelMedium,
                color = Ink,
            )
            Text(
                objectif.description,
                style = MaterialTheme.typography.bodySmall,
                color = Ink2,
            )
        }
    }
}
