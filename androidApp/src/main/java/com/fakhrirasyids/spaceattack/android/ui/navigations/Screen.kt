package com.fakhrirasyids.spaceattack.android.ui.navigations

import com.fakhrirasyids.spaceattack.android.utils.enums.PlatformScreen

sealed class Screen(val route: String) {
    data object Splash : Screen(PlatformScreen.SPLASH.name)
    data object Home : Screen(PlatformScreen.HOME.name)
    data object Leaderboard : Screen(PlatformScreen.LEADERBOARD.name)

    data object Game :
        Screen("${PlatformScreen.GAME.name}/{$EXTRA_PLAYER_NAME}/{$EXTRA_DIFFICULTY}") {
        fun createRoute(playerName: String, difficulty: String) =
            "${PlatformScreen.GAME.name}/$playerName/$difficulty"
    }

    companion object {
        const val EXTRA_PLAYER_NAME = "extra_player_name"
        const val EXTRA_DIFFICULTY = "extra_difficulty"
    }
}