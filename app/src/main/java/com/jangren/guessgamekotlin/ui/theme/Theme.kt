package com.jangren.guessgamekotlin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    onPrimary = primaryColor,
    secondary = secondaryColor,
    onSecondary = secondaryColor,
    background = bg_color,
    onBackground = bg_color,
    primaryContainer = primaryColor,
    onPrimaryContainer = bg_color,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = primaryColor,
    secondary = secondaryColor,
    onSecondary = secondaryColor,
    primaryContainer = primaryColor,
    onPrimaryContainer = bg_color,
    background = bg_color,
    onBackground = bg_color,
    
)

@Composable
fun GuessGameKotlinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}