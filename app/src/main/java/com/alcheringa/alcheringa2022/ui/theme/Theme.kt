package com.alcheringa.alcheringa2022.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
    primary = lighterPurple,
    primaryVariant = darkerPurple,
    secondary = green,
    background = darkBar,
    onBackground = lightBar,
    onSurface = darkBar,
    secondaryVariant = darkerGreen,
    

)

private val LightColorPalette = lightColors(
    primary = lighterPurple,
    primaryVariant = darkerPurple,
    secondary = lighterGreen,
    background = lightBar,
    onBackground = darkBar,
    onSurface = lightBar,
    secondaryVariant = darkerGreen





    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun Alcheringa2022Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}