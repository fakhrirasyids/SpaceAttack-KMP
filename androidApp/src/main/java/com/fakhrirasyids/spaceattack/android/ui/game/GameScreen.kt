package com.fakhrirasyids.spaceattack.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.fakhrirasyids.spaceattack.SharedRes
import com.fakhrirasyids.spaceattack.android.ui.widgets.ScrollingStarsBackground
import com.fakhrirasyids.spaceattack.android.utils.ComposableLifecycle
import com.fakhrirasyids.spaceattack.android.utils.DensityConverter.asDp
import com.fakhrirasyids.spaceattack.android.utils.DensityConverter.asPx
import com.fakhrirasyids.spaceattack.android.utils.advanceShadow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = koinViewModel()
    ) {
    val context = LocalContext.current

    val paddingToDrawPlayerX = 200

    val playerWidthDp = gameViewModel.playerWidthDp.asDp
    val playerHeightDp = gameViewModel.playerHeightDp.asDp

    val enemyWidthDp = gameViewModel.enemyWidthDp.asDp
    val enemyHeightDp = gameViewModel.enemyHeightDp.asDp

    val bulletSizeDp = gameViewModel.bulletSizeDp.asDp

    val playerWidthPx = gameViewModel.playerWidthDp.asPx

    val isPaused by gameViewModel.isPaused.collectAsState()
    val playerX by gameViewModel.playerPosition.collectAsState()
    val bullets by gameViewModel.bullets.collectAsState()
    val enemies by gameViewModel.enemies.collectAsState()
    val enemyBullets by gameViewModel.enemyBullets.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val lives by gameViewModel.lives.collectAsState()

    val screenWidth by gameViewModel.screenWidth.collectAsState()
    val screenHeight by gameViewModel.screenHeight.collectAsState()

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> gameViewModel.onPause()
            Lifecycle.Event.ON_RESUME -> gameViewModel.onResume()
            else -> {}
        }
    }
    LaunchedEffect(screenWidth, screenHeight) {
        if (screenWidth > 0 && screenHeight > 0) {
            gameViewModel.startGameLoops()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                gameViewModel.screenWidth.value = layoutCoordinates.size.width
                gameViewModel.screenHeight.value = layoutCoordinates.size.height
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    gameViewModel.movePlayer(dragAmount.toInt(), screenWidth, playerWidthPx)
                }
            }
    ) {
        ScrollingStarsBackground(isPaused = isPaused, modifier = Modifier.fillMaxSize())

        // Player
        Image(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .offset {
                    IntOffset(
                        playerX,
                        screenHeight - paddingToDrawPlayerX
                    )
                }
                .size(width = playerWidthDp, height = playerHeightDp)
                .advanceShadow(
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context))
                ),
            painter = painterResource(imageResource = SharedRes.images.asset_player_point),
            contentDescription = null
        )

        // Player Bullets
        bullets.forEach { bullet ->
            Image(
                modifier = Modifier
                    .offset { IntOffset(bullet.x.toInt(), bullet.y.toInt()) }
                    .size(bulletSizeDp),
                painter = painterResource(
                    imageResource = SharedRes.images.asset_player_bullet_point
                ),
                contentDescription = null
            )
        }

        // Enemies
        enemies.forEach { enemy ->
            Image(
                modifier = Modifier
                    .offset { IntOffset(enemy.position.x.toInt(), enemy.position.y.toInt()) }
                    .size(width = enemyWidthDp, height = enemyHeightDp)
                    .advanceShadow(
                        color = if (enemy.isRed) Color(
                            SharedRes.colors.primaryRed.getColor(
                                context
                            )
                        ) else Color(SharedRes.colors.primaryGreen.getColor(context)),
                    ),
                painter = painterResource(
                    imageResource = if (enemy.isRed) SharedRes.images.asset_enemy_red_one_point else SharedRes.images.asset_enemy_green_one_point
                ),
                contentDescription = null
            )
        }

        // Enemy Bullets
        enemyBullets.forEach { bullet ->
            Image(
                modifier = Modifier
                    .offset { IntOffset(bullet.x.toInt(), bullet.y.toInt()) }
                    .size(bulletSizeDp),
                painter = painterResource(
                    imageResource = SharedRes.images.asset_enemy_bullet_point
                ),
                contentDescription = null
            )
        }

        // Score and Lives
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(SharedRes.strings.game_score, score),
                fontSize = 12.sp,
                fontFamily = fontFamilyResource(SharedRes.fonts.pressstart2p_regular),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(SharedRes.strings.game_lives, lives),
                fontSize = 12.sp,
                fontFamily = fontFamilyResource(SharedRes.fonts.pressstart2p_regular),
                color = Color.White
            )
        }
    }
}