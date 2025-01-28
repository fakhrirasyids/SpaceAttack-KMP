package com.fakhrirasyids.spaceattack.android.ui.screens.home.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fakhrirasyids.spaceattack.SharedRes
import com.fakhrirasyids.spaceattack.android.utils.enums.GameDifficulty
import com.fakhrirasyids.spaceattack.android.utils.helpers.octagonBackground
import com.fakhrirasyids.spaceattack.utils.PlayerHelper
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeGameDifficultyDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDifficultySelected: (String, String) -> Unit
) {
    val context = LocalContext.current
    val playerName = remember { mutableStateOf(PlayerHelper().generatePlayerName()) }
    val snackbarHostState = remember { SnackbarHostState() }

    Dialog(onDismissRequest = onDismiss) {
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
                    text = stringResource(resource = SharedRes.strings.home_player_name),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
                        .clickable { onDismiss() },
                    fontSize = 14.sp,
                    color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                    fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                )

                TextField(
                    value = playerName.value,
                    onValueChange = { updatedPlayerName -> playerName.value = updatedPlayerName },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .octagonBackground(
                            color = Color(SharedRes.colors.colorPrimary.getColor(context)),
                            borderColor = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                            borderWidth = 12f,
                            edgeInsetFraction = 0.01f
                        ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                        color = Color(SharedRes.colors.colorOnPrimary.getColor(context))
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(resource = SharedRes.strings.home_player_name_placeholder),
                            fontSize = 12.sp,
                            fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular),
                            color = Color(SharedRes.colors.colorOnPrimary.getColor(context)).copy(
                                alpha = 0.5f
                            )
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White
                    )
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
                Text(
                    text = stringResource(resource = SharedRes.strings.home_game_difficulty_select),
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
                        if (playerName.value.isBlank()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                snackbarHostState.showSnackbar("Please fill in the player name!")
                            }
                        } else {
                            onDifficultySelected(playerName.value, GameDifficulty.Easy.name)
                            onDismiss()
                        }
                    }) {
                    Text(
                        text = stringResource(resource = SharedRes.strings.home_game_difficulty_easy),
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
                        if (playerName.value.isBlank()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                snackbarHostState.showSnackbar("Please fill in the player name!")
                            }
                        } else {
                            onDifficultySelected(playerName.value, GameDifficulty.Hard.name)
                            onDismiss()
                        }
                    }) {
                    Text(
                        text = stringResource(resource = SharedRes.strings.home_game_difficulty_hard),
                        fontSize = 12.sp,
                        color = Color(SharedRes.colors.colorOnPrimary.getColor(context)),
                        fontFamily = fontFamilyResource(fontResource = SharedRes.fonts.pressstart2p_regular)
                    )
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}