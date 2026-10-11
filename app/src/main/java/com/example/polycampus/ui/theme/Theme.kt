package com.example.polycampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PolyCampusColors = lightColorScheme(
    primary = Color(0xFF00355F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF0F4C81),
    onPrimaryContainer = Color(0xFF8EBDF9),
    secondary = Color(0xFF006B5E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF94F0DF),
    surface = Color(0xFFF8F9FF),
    surfaceContainerLow = Color(0xFFEFF4FF),
    surfaceContainer = Color(0xFFE5EEFF),
    surfaceContainerHigh = Color(0xFFE0E9F9),
    surfaceContainerHighest = Color(0xFFDAE3F4),
    onSurface = Color(0xFF131C28),
    onSurfaceVariant = Color(0xFF42474F),
    outline = Color(0xFF727780),
    outlineVariant = Color(0xFFC2C7D1),
)

@Composable
fun PolyCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = PolyCampusColors, content = content)
}
