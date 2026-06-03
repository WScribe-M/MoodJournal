package com.example.moodjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.moodjournal.R


@Composable
fun Lumi(size: Dp) {
    Image(
        painter = painterResource(R.drawable.lumi_neutral),
        contentDescription = "Lumi",
        modifier = Modifier.size(size)
    )
}