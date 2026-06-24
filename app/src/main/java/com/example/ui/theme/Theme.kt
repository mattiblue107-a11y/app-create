package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ElegantDarkColorScheme = darkColorScheme(
    primary = ElegantPrimary,
    onPrimary = ElegantOnPrimary,
    primaryContainer = ElegantContainer,
    onPrimaryContainer = ElegantText,
    secondary = ElegantSecondary,
    onSecondary = ElegantOnSecondary,
    secondaryContainer = ElegantCardVariant,
    onSecondaryContainer = ElegantText,
    background = ElegantDarkBg,
    onBackground = ElegantText,
    surface = ElegantSurface,
    onSurface = ElegantText,
    surfaceVariant = ElegantContainer,
    onSurfaceVariant = ElegantTextMuted,
    outline = ElegantOutline
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ElegantDarkColorScheme,
        typography = Typography,
        content = content
    )
}
