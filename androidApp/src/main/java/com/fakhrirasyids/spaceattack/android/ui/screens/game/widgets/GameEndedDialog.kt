package com.fakhrirasyids.spaceattack.android.ui.screens.game.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fakhrirasyids.spaceattack.SharedRes
import com.fakhrirasyids.spaceattack.android.data.models.PlayerResult
import com.fakhrirasyids.spaceattack.android.utils.enums.GameDifficulty
import com.fakhrirasyids.spaceattack.android.utils.helpers.advanceShadow
import com.fakhrirasyids.spaceattack.android.utils.helpers.octagonBackground
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GameEndedDialog(
    modifier: Modifier = Modifier,
    playerResult: PlayerResult,
    onDismiss: () -> Unit = {},
    onLeaderboardSelected: () -> Unit = {},
    onLeaveSelected: () -> Unit = {}
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = {}) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .octagonBackground(
                        color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                        borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        borderWidth = 12f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = playerResult.playerName,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
                        .clickable { onDismiss() },
                    fontSize = 16.sp,
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                )

                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(24.dp)
                        .size(width = 40.dp, height = 20.dp)
                        .advanceShadow(
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context))
                        ),
                    painter = painterResource(imageResource = SharedRes.images.asset_player_point),
                    contentDescription = null
                )

                Text(
                    text = stringResource(SharedRes.strings.game_score, playerResult.playerScore),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
                        .clickable { onDismiss() },
                    fontSize = 16.sp,
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .octagonBackground(
                        color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                        borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        borderWidth = 12f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                        .octagonBackground(
                            color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                            borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                            borderWidth = 12f,
                            edgeInsetFraction = 0.01f
                        ),
                    onClick = {
                        onLeaderboardSelected()
                    }) {
                    Text(
                        text = stringResource(resource = SharedRes.strings.home_leaderboards),
                        fontSize = 12.sp,
                        color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                    )
                }

                Button(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .octagonBackground(
                            color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                            borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                            borderWidth = 12f,
                            edgeInsetFraction = 0.01f
                        ),
                    onClick = {
                        onLeaveSelected()
                        onDismiss()
                    }) {
                    Text(
                        text = stringResource(resource = SharedRes.strings.game_leave),
                        fontSize = 12.sp,
                        color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                    )
                }
            }
        }
    }
}