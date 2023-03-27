package com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.LightGray,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.Red,
    surface = Color.Green,
    onPrimary = Color.Blue,
    onSecondary = Color.Yellow,
    onBackground = Color.White,//ok
    onSurface = Color.White,//ok
)

@Composable
fun Project01Theme(darkTheme: Boolean = isSystemInDarkTheme(),
                   content: @Composable () -> Unit) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}