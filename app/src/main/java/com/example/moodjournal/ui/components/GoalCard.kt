package com.example.moodjournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodjournal.data.model.Objectif
import com.example.moodjournal.ui.theme.Terracotta

@Composable
fun GoalCard(onClick: () -> Unit, objectif: Objectif, isSelected: Boolean) {
    Card(
        onClick = { onClick() },
        modifier = Modifier.height(120.dp),
        border = if (isSelected) BorderStroke(1.dp, Terracotta) else null,
        colors = if (isSelected) CardDefaults.cardColors(containerColor = Color(0xFFF6D9C8)) else CardDefaults.cardColors(containerColor = Color(0xFFFAF5EC))
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(imageVector = objectif.icone, contentDescription = null)
            Text(objectif.titre)
            Text(objectif.description)
        }
    }
}