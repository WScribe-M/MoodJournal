package com.example.moodjournal.ui.components

import androidx.compose.ui.graphics.Color
import com.example.moodjournal.ui.theme.Blush
import com.example.moodjournal.ui.theme.BlushBg
import com.example.moodjournal.ui.theme.Cream3
import com.example.moodjournal.ui.theme.Marigold
import com.example.moodjournal.ui.theme.MarigoldBg
import com.example.moodjournal.ui.theme.Sage
import com.example.moodjournal.ui.theme.SageBg
import com.example.moodjournal.ui.theme.Sky
import com.example.moodjournal.ui.theme.SkyBg
import com.example.moodjournal.ui.theme.Terracotta
import com.example.moodjournal.ui.theme.TerracottaBg

data class EmotionData(
    val id: String,
    val label: String,
    val emoji: String,
    val bgColor: Color,
    val inkColor: Color,
)

val EMOTIONS = listOf(
    EmotionData("joy",         "Joie",          "🌞", MarigoldBg,   Marigold),
    EmotionData("calm",        "Calme",         "🌿", SageBg,       Sage),
    EmotionData("grateful",    "Reconnaissant", "💛", MarigoldBg,   Marigold),
    EmotionData("tired",       "Fatigué",       "🌙", SkyBg,        Sky),
    EmotionData("sad",         "Triste",        "🌧", SkyBg,        Sky),
    EmotionData("anxious",     "Anxieux",       "🌪", BlushBg,      Blush),
    EmotionData("overwhelmed", "Submergé",      "🌊", BlushBg,      Blush),
    EmotionData("angry",       "En colère",     "🔥", TerracottaBg, Terracotta),
)

fun emotionById(id: String): EmotionData? = EMOTIONS.find { it.id == id }

fun emotionColorById(id: String?): Color = when (id) {
    "joy", "grateful"        -> Marigold
    "calm"                   -> Sage
    "tired", "sad"           -> Sky
    "anxious", "overwhelmed" -> Blush
    "angry"                  -> Terracotta
    else                     -> Cream3
}
