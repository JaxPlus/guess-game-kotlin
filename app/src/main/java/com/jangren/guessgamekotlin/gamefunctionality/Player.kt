package com.jangren.guessgamekotlin.gamefunctionality

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.findActivity
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.random.nextInt

enum class GameDifficulty {
    EASY,
    MEDIUM,
    HARD
}

@Serializable
data class Player(val difficulty: GameDifficulty) {
    var hp = 0
    private var max = 0
    private var min = 0
    
    init {
        when (difficulty) {
            GameDifficulty.EASY -> setGameState(8, 0, 50)
            GameDifficulty.MEDIUM -> setGameState(10, 0, 200)
            GameDifficulty.HARD -> setGameState(12, 0, 500)
        }
    }
    
    private fun setGameState(hp: Int, min: Int, max: Int) {
        this.hp = hp
        this.min = min
        this.max = max
    }
    
    fun getRange(): IntRange {
        return min .. max
    }
    
    fun loseHp(): Boolean {
        hp--

        return hp > 0
    }
}

class Game(private val player: Player, private val context: ComponentActivity) {
    private var currentNumberToGuess = 0
    var attempts = 0
    var isGameEnded = false
    
    init {
        currentNumberToGuess = Random.nextInt(player.getRange())
    }
    
    fun guessNumber(number: Int): String {
        val result = when {
            number < currentNumberToGuess -> {
                if (player.loseHp())
                    context.getString(R.string.your_guess_is_higher)
                else {
                    endGame()
                    context.getString(R.string.you_lost)
                }
            }
            number > currentNumberToGuess -> {
                if (player.loseHp())
                    context.getString(R.string.your_guess_is_lower)
                else {
                    endGame()
                    context.getString(R.string.you_lost)
                }
            }
            number == currentNumberToGuess -> {
                endGame()
                context.getString(R.string.you_won)
            }
            else -> ""
        }
        
        attempts++
        return result
    }
    
    private fun endGame() {
        isGameEnded = true
    }
}