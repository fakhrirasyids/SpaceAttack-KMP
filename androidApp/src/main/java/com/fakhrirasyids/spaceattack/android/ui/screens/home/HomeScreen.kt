package com.fakhrirasyids.spaceattack.android.ui.screens.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.spaceattack.SharedRes
import com.fakhrirasyids.spaceattack.android.ui.screens.home.widgets.HomeGameDifficultyDialog
import com.fakhrirasyids.spaceattack.android.ui.widgets.ScrollingStarsBackground
import com.fakhrirasyids.spaceattack.android.utils.helpers.advanceShadow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToGame: (String, String) -> Unit,
    navigateToLeaderboards: () -> Unit,
) {
    val context = LocalContext.current

    var showDifficultyDialog by remember { mutableStateOf(false) }

    var startAnimation by remember { mutableStateOf(false) }
    var endAnimation by remember { mutableStateOf(false) }

    val sizeAnimation = animateDpAsState(
        targetValue = if (startAnimation) 350.dp else 0.dp,
        animationSpec = tween(durationMillis = 1500), label = "SplashScreenSizeAnimation"
    )

    val offsetAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f, // 1f means fully at the bottom
        animationSpec = tween(durationMillis = 1500), label = "SplashScreenOffsetAnimation"
    )

    val menuAlphaAnimation = animateFloatAsState(
        targetValue = if (endAnimation) 1F else 0F,
        animationSpec = tween(durationMillis = 300), label = "SplashScreenAlphaAnimation"
    )

    LaunchedEffect(key1 = true) {
        delay(300)
        startAnimation = true
        delay(1200)
        endAnimation = true
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScrollingStarsBackground(false, scrollSpeed = 2f)

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(sizeAnimation.value)
                    .offset {
                        IntOffset(
                            0,
                            (offsetAnimation.value).toInt()
                        )
                    }
                    .alpha(if (startAnimation) 1f else 0f),
                painter = painterResource(imageResource = SharedRes.images.logo_spaceattack),
                contentDescription = stringResource(resource = SharedRes.strings.app_name),
            )

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .alpha(menuAlphaAnimation.value)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(resource = SharedRes.strings.home_start_game),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                    modifier = Modifier
                        .padding(12.dp)
                        .advanceShadow(
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(
                                alpha = 0.2f
                            )
                        )
                        .clickable {
                            showDifficultyDialog = true
                        }
                )


                Text(
                    text = stringResource(resource = SharedRes.strings.home_leaderboards),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                    modifier = Modifier
                        .padding(12.dp)
                        .advanceShadow(
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(
                                alpha = 0.2f
                            )
                        )
                        .clickable {
                            navigateToLeaderboards()
                        }
                )


                Text(
                    text = stringResource(resource = SharedRes.strings.home_settings),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                    modifier = Modifier
                        .padding(12.dp)
                        .advanceShadow(
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(
                                alpha = 0.2f
                            )
                        )
                        .clickable {

                        }
                )

                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = stringResource(resource = SharedRes.strings.home_instructions),
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(alpha = 0.8f),
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                    modifier = Modifier
                        .padding(24.dp)
                        .advanceShadow(
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(
                                alpha = 0.2f
                            )
                        )
                )
            }

            if (showDifficultyDialog) {
                HomeGameDifficultyDialog(
                    onDismiss = { showDifficultyDialog = false },
                    onDifficultySelected = { playerName, difficulty ->
                        navigateToGame(playerName, difficulty)
                    }
                )
            }
        }
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
