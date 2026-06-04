package com.example.moodjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.moodjournal.ui.theme.Ink
import com.example.moodjournal.ui.theme.Ink2
import com.example.moodjournal.ui.theme.Ink3
import com.example.moodjournal.ui.theme.Line
import com.example.moodjournal.ui.theme.Paper
import com.example.moodjournal.ui.theme.Terracotta

private val fieldShape = RoundedCornerShape(16.dp)

@Composable
fun MoodTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Ink2,
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Ink),
            cursorBrush = SolidColor(Terracotta),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Paper, fieldShape)
                .border(1.dp, Line, fieldShape)
                .padding(horizontal = 16.dp),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(placeholder, style = MaterialTheme.typography.bodyLarge.copy(color = Ink3))
                    }
                    inner()
                }
            },
        )
    }
}
