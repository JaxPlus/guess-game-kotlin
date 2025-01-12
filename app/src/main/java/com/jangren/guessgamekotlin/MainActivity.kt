package com.jangren.guessgamekotlin

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jangren.guessgamekotlin.composables.AlertDialogNickForm
import com.jangren.guessgamekotlin.composables.GameDifficultyView
import com.jangren.guessgamekotlin.composables.GameEndScreen
import com.jangren.guessgamekotlin.composables.GameScreen
import com.jangren.guessgamekotlin.composables.LeaderboardScreen
import com.jangren.guessgamekotlin.composables.LevelScreen
import com.jangren.guessgamekotlin.composables.MenuScreen
import com.jangren.guessgamekotlin.gamefunctionality.GameState
import com.jangren.guessgamekotlin.gamefunctionality.Player
import com.jangren.guessgamekotlin.navtypes.GameStateNavType
import com.jangren.guessgamekotlin.navtypes.PlayerNavType
import com.jangren.guessgamekotlin.ui.theme.GuessGameKotlinTheme
import com.jangren.guessgamekotlin.ui.theme.bg_color
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessGameKotlinTheme {
                Column(
                    modifier = Modifier
                        .background(bg_color)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = Routes.menu,
                    ) {
                        composable(Routes.menu) {
                            MenuScreen(navController)
                        }
                        composable(Routes.gameDifficulty) {
                            GameDifficultyView(navController)
                        }
                        composable(Routes.leaderboard) {
                            LeaderboardScreen(navController)
                        }
                        composable(Routes.level) {
                            LevelScreen(navController)
                        }
                        composable<Routes.GameScreenRoute>(
                            typeMap = mapOf(
                                typeOf<Player>() to PlayerNavType.playerType,
                            )
                        ) { 
                            val args = it.toRoute<Routes.GameScreenRoute>()
                            
                            GameScreen(
                                player = args.player,
                                navController = navController
                            )
                        }
                        composable<Routes.GameEndScreenRoute> (
                            typeMap = mapOf(
                                typeOf<GameState>() to GameStateNavType.gameStateType
                            )
                        ) { 
                            val args = it.toRoute<Routes.GameEndScreenRoute>()
                            
                            GameEndScreen(
                                gameState = args.gameState, 
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}