package com.example.moodjournal.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary            = Terracotta,
    onPrimary          = Paper,
    primaryContainer   = TerracottaBg,
    onPrimaryContainer = TerracottaDark,

    secondary          = Sage,
    onSecondary        = Paper,
    secondaryContainer = SageBg,
    onSecondaryContainer = SageDark,

    tertiary           = Marigold,
    tertiaryContainer  = MarigoldBg,

    background         = Cream,
    onBackground       = Ink,
    surface            = Paper,
    onSurface          = Ink,
    surfaceVariant     = Cream2,
    onSurfaceVariant   = Ink2,
    outline            = Cream3,
)

private val DarkColorScheme = darkColorScheme(
    primary            = DarkTerra,
    onPrimary          = DarkBg,
    primaryContainer   = DarkTerracottaBg,
    onPrimaryContainer = TerracottaLight,

    secondary          = Color(0xFF9CB395),
    onSecondary        = DarkBg,
    secondaryContainer = DarkSageBg,
    onSecondaryContainer = Color(0xFFD9DFD2),

    background         = DarkBg,
    onBackground       = DarkInk,
    surface            = DarkPaper,
    onSurface          = DarkInk,
    surfaceVariant     = DarkBg2,
    onSurfaceVariant   = DarkInk2,
    outline            = DarkBg3,
)

@Composable
fun MoodJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
