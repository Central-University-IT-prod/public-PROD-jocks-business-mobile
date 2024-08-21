package ru.jocks.swipecsadbusiness.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.jocks.swipecsadbusiness.R


val inter = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_extralight, FontWeight.ExtraLight),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold),
)

object AdditionalTextStyles {
    val analyticsTitle = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
    val FormRatingText = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    val FormRatingTextSmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Light,
        fontSize = 8.sp
    )
    val FormRatingTitleSmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
}