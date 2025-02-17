package com.fakhrirasyids.spaceattack.android.ui.theme

import android.content.Context
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.spaceattack.SharedRes

@Composable
fun SpaceAttackTheme(
    context: Context,
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme(
        primary = Color(SharedRes.colors.colorPrimary.getColor(context)),
        secondary = Color(0xFF03DAC5),
        tertiary = Color(0xFF3700B3),
        background = Color(SharedRes.colors.colorPrimary.getColor(context))
    )

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
