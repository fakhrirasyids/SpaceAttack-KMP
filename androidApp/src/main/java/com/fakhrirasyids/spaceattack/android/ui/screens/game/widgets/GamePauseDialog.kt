package com.fakhrirasyids.spaceattack.android.ui.screens.game.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fakhrirasyids.spaceattack.SharedRes
import com.fakhrirasyids.spaceattack.android.utils.enums.GameDifficulty
import com.fakhrirasyids.spaceattack.android.utils.helpers.octagonBackground
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GamePauseDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onResumeSelected: () -> Unit = {},
    onEndSelected: () -> Unit = {},
    onLeaveSelected: () -> Unit = {}
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .octagonBackground(
                    color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                    borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                    borderWidth = 12f
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(resource = SharedRes.strings.game_paused),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .clickable { onDismiss() },
                fontSize = 14.sp,
                color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
            )

            Button(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .octagonBackground(
                        color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                        borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        borderWidth = 12f,
                        edgeInsetFraction = 0.01f
                    ),
                onClick = {
                    onResumeSelected()
                    onDismiss()
                }) {
                Text(
                    text = stringResource(resource = SharedRes.strings.game_resume),
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
                    onEndSelected()
                    onDismiss()
                }) {
                Text(
                    text = stringResource(resource = SharedRes.strings.game_end),
                    fontSize = 12.sp,
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                )
            }

            Button(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
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