package com.jangren.guessgamekotlin

import com.jangren.guessgamekotlin.gamefunctionality.GameState
import com.jangren.guessgamekotlin.gamefunctionality.Player
import kotlinx.serialization.Serializable

object Routes {
    const val menu = "menu"
    const val gameDifficulty = "gameDiff"
    const val level = "shop"
    const val leaderboard = "leaderboard"

    @Serializable
    data class GameScreenRoute(
        val player: Player
    )
    @Serializable
    data class GameEndScreenRoute(
        val gameState: GameState
    )
}