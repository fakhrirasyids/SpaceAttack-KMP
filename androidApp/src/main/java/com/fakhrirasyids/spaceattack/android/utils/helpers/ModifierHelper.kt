package com.fakhrirasyids.spaceattack.android.utils.helpers

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.advanceShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 24.dp,
    blurRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 0.5f,
) = drawBehind {
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.dp.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx()
        val rightPixel = (this.size.width)
        val bottomPixel = (this.size.height + spreadPixel)

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}

fun Modifier.octagonBackground(
    color: Color,
    borderColor: Color,
    borderWidth: Float,
    edgeInsetFraction: Float = 0.05f, // fill with max value of 0.1F
): Modifier = this.drawBehind {
    val octagonPath = Path().apply {
        val edgeInset = size.width * edgeInsetFraction

        moveTo(edgeInset, 0f)
        lineTo(size.width - edgeInset, 0f)
        lineTo(size.width, edgeInset)
        lineTo(size.width, size.height - edgeInset)
        lineTo(size.width - edgeInset, size.height)
        lineTo(edgeInset, size.height)
        lineTo(0f, size.height - edgeInset)
        lineTo(0f, edgeInset)
        close()
    }

    drawPath(
        path = octagonPath,
        color = borderColor,
        style = Stroke(width = borderWidth)
    )

    drawPath(
        path = octagonPath,
        color = color
    )
}
