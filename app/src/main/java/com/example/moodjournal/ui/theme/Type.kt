package com.example.moodjournal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moodjournal.R

val DmSerifDisplay = FontFamily(Font(R.font.dm_serif_display_regular))
val Geist = FontFamily(Font(R.font.geist_regular))
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(fontFamily = DmSerifDisplay),
    headlineLarge = TextStyle(fontFamily = DmSerifDisplay),
    titleLarge = TextStyle(fontFamily = DmSerifDisplay),
    bodyLarge = TextStyle(fontFamily = Geist),
    bodyMedium = TextStyle(fontFamily = Geist),
    labelSmall = TextStyle(fontFamily = Geist),
)