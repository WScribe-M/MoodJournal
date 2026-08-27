package com.example.moodjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.moodjournal.R

enum class LumiMood { NEUTRAL, CALM, HAPPY, THINKING, CONCERNED }

@Composable
fun Lumi(size: Dp, mood: LumiMood = LumiMood.NEUTRAL) {
    val res = when (mood) {
        LumiMood.NEUTRAL   -> R.drawable.lumi_neutral
        LumiMood.CALM      -> R.drawable.lumi_calm
        LumiMood.HAPPY     -> R.drawable.lumi_happy
        LumiMood.THINKING  -> R.drawable.lumi_thinking
        LumiMood.CONCERNED -> R.drawable.lumi_concerned
    }
    Image(
        painter = painterResource(res),
        contentDescription = "Lumi",
        modifier = Modifier.size(size),
    )
}
