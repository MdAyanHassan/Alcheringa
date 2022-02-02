package com.example.alcheringa2022.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.alcheringa2022.R
val clash= FontFamily(
        Font(R.font.clashdisplay),
        Font(R.font.clashdisplay_bold, FontWeight.Bold),
        Font(R.font.clashdisplay_extralight, FontWeight.ExtraLight),
        Font(R.font.clashdisplay_light, FontWeight.Light),
        Font(R.font.clashdisplay_medium, FontWeight.Medium),
        Font(R.font.clashdisplay_semibold, FontWeight.SemiBold)
)

val hk_grotesk= FontFamily(
        Font(R.font.hk_grotesk)
)
// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = clash,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
        ),
        h1 = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = clash ),
        h2 = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium, fontFamily = clash),

        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
