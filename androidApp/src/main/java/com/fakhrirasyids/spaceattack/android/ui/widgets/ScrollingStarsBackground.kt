package com.fakhrirasyids.spaceattack.android.ui.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.translate
import com.fakhrirasyids.spaceattack.SharedRes
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay

@Composable
fun ScrollingStarsBackground(
    isPaused: Boolean,
    modifier: Modifier = Modifier,
    scrollSpeed: Float = 5f
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    val painter = painterResource(imageResource = SharedRes.images.asset_stars_bg)

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val painterWidth = painter.intrinsicSize.width
            val painterHeight = painter.intrinsicSize.height
            val scaleX = size.width / painterWidth
            val scaleY = size.height / painterHeight
            val scale = maxOf(scaleX, scaleY)

            translate(top = offsetY % size.height) {
                with(painter) {
                    draw(
                        size = androidx.compose.ui.geometry.Size(
                            painterWidth * scale,
                            painterHeight * scale
                        )
                    )
                }
            }

            translate(top = (offsetY % size.height) - size.height) {
                with(painter) {
                    draw(
                        size = androidx.compose.ui.geometry.Size(
                            painterWidth * scale,
                            painterHeight * scale
                        )
                    )
                }
            }
        }

        LaunchedEffect(isPaused) {
            while (true) {
                if (!isPaused) {
                    offsetY += scrollSpeed
                }
                delay(10L)
            }
        }
    }
}


