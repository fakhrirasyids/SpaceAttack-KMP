package com.fakhrirasyids.spaceattack.android.ui

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakhrirasyids.spaceattack.android.ui.navigations.Screen
import com.fakhrirasyids.spaceattack.android.ui.screens.game.GameScreen
import com.fakhrirasyids.spaceattack.android.ui.screens.game.GameViewModel
import com.fakhrirasyids.spaceattack.android.ui.screens.home.HomeScreen
import com.fakhrirasyids.spaceattack.android.ui.screens.leaderboard.LeaderboardScreen
import com.fakhrirasyids.spaceattack.android.ui.screens.splash.SplashScreen
import com.fakhrirasyids.spaceattack.android.ui.theme.SpaceAttackTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SpaceAttackApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) Screen.Splash.route else Screen.Home.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navigateToGame = { playerName, difficulty ->
                    navController.navigate(
                        Screen.Game.createRoute(playerName, difficulty)
                    )
                },
                navigateToLeaderboards = {

                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument(
                    Screen.EXTRA_PLAYER_NAME,
                ) { type = NavType.StringType },
                navArgument(
                    Screen.EXTRA_DIFFICULTY,
                ) { type = NavType.StringType },
            )
        ) {
            val playerName = it.arguments?.getString(Screen.EXTRA_PLAYER_NAME) ?: ""
            val difficulty = it.arguments?.getString(Screen.EXTRA_DIFFICULTY) ?: ""
            val gameViewModel: GameViewModel = koinViewModel(
                parameters = { parametersOf(playerName, difficulty) }
            )

            GameScreen(
                gameViewModel = gameViewModel,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardScreen()
        }
    }
}