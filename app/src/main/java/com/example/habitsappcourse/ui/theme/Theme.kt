package com.example.habitsappcourse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Background,
    tertiary = Accent,
    background = Background,
    onPrimary = Accent,
    onSecondary = Primary,
    onBackground = Primary,
    onTertiary = Primary,
    surface = Background,
    onSurface = Accent
)

@Composable
fun HabitsAppCourseTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}