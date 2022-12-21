package com.alcheringa.alcheringa2022.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
        primary = blu,
        primaryVariant = darkBlu,
        secondary = midBlack,
        background = black,
        onBackground = white,
        onSurface = highBlack,
        secondaryVariant = midWhite

)

private val LightColorPalette = lightColors(
        primary = blu,
        primaryVariant = darkBlu,
        secondary = black,
        background = white,
        onBackground = black,
        onSurface = midWhite,
        secondaryVariant = midBlack





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