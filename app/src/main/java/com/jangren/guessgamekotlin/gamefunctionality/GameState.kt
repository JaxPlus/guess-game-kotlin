package com.jangren.guessgamekotlin.gamefunctionality

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val attempts: Int,
    val hpLeft: Int,
    val isGameWon: Boolean,
    val difficulty: GameDifficulty,
)
