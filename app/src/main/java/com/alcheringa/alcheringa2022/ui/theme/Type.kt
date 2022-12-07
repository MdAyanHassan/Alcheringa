package com.alcheringa.alcheringa2022.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.R
val clash= FontFamily(
        Font(R.font.clash_display_400),
        Font(R.font.clash_display_700, FontWeight.Bold),
        Font(R.font.clash_display_200, FontWeight.ExtraLight),
        Font(R.font.clash_display_300, FontWeight.Light),
        Font(R.font.clash_display_500, FontWeight.Medium),
        Font(R.font.clash_display_600, FontWeight.SemiBold)
)

val hk_grotesk= FontFamily(
        Font(R.font.hk_grotesk_400),
        Font(R.font.hk_grotesk_700, FontWeight.Bold),
        Font(R.font.hk_grotesk_300, FontWeight.Light),
        Font(R.font.hk_grotesk_500, FontWeight.Medium),
        Font(R.font.hk_grotesk_600, FontWeight.SemiBold)
)
val vanguard= FontFamily(
        Font(R.font.vanguardregular),
        Font(R.font.vanguardbold, FontWeight.Bold),
        Font(R.font.vanguardmedium, FontWeight.Medium),
        Font(R.font.venguardsemi, FontWeight.SemiBold)
)

val aileron= FontFamily(
        Font(R.font.aileron_regular, FontWeight.Normal),
        Font(R.font.aileron_bold, FontWeight.Bold),
        Font(R.font.aileron_semibold, FontWeight.SemiBold)
)
val star_guard= FontFamily(
        Font(R.font.starguard, FontWeight.Normal),

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
