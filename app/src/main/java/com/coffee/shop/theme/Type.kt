package com.coffee.shop.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cofee.shop.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val soraFamily = FontFamily(
    Font(R.font.sora_thin, FontWeight.Thin),
    Font(R.font.sora_light, FontWeight.Light),
    Font(R.font.sora_regular, FontWeight.Normal),
    Font(R.font.sora_regular, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.sora_medium, FontWeight.Medium),
    Font(R.font.sora_bold, FontWeight.Bold),
    Font(R.font.sora_semibold, FontWeight.SemiBold),
)