package com.jangren.guessgamekotlin.composables

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.Routes
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.gamefunctionality.GameDifficulty
import com.jangren.guessgamekotlin.gamefunctionality.GameState
import com.jangren.guessgamekotlin.ui.theme.bg_color
import com.jangren.guessgamekotlin.ui.theme.primaryColor
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Composable
fun GameEndScreen(gameState: GameState, navController: NavController) {
    if (gameState.attempts == -1 || gameState.hpLeft == -1) {
        navController.navigate(Routes.menu)
    }
    
    val context = LocalContext.current.findActivity()
    val sharedPref = context?.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )
    
    val title by remember {
        mutableIntStateOf(if (gameState.isGameWon) R.string.you_won else R.string.you_lost)
    }
    var exp by remember {
        mutableIntStateOf(0)
    }
    var score by remember {
        mutableIntStateOf(0)
    }

    exp = when (gameState.difficulty) {
        GameDifficulty.EASY -> gameState.attempts * gameState.hpLeft * 0.8
        GameDifficulty.MEDIUM -> gameState.attempts * gameState.hpLeft * 1.2
        GameDifficulty.HARD -> gameState.attempts * gameState.hpLeft * 2.3
    }.roundToInt()

    score = when (gameState.difficulty) {
        GameDifficulty.EASY -> calculateScore(gameState.isGameWon, gameState.hpLeft, gameState.attempts, 5)
        GameDifficulty.MEDIUM -> calculateScore(gameState.isGameWon, gameState.hpLeft, gameState.attempts, 10)
        GameDifficulty.HARD -> calculateScore(gameState.isGameWon, gameState.hpLeft, gameState.attempts, 20)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(title),
            fontSize = 36.sp,
            modifier = Modifier.padding(5.dp),
            color = primaryColor
        )
        Text(
            text = stringResource(R.string.score, score),
            fontSize = 22.sp,
            modifier = Modifier.padding(5.dp),
            color = primaryColor
        )
        Text(
            text = stringResource(R.string.attempts, gameState.attempts),
            fontSize = 22.sp,
            modifier = Modifier.padding(5.dp),
            color = primaryColor
        )
        Text(
            text = stringResource(R.string.bubble_tea_life_left, gameState.hpLeft),
            fontSize = 22.sp,
            modifier = Modifier.padding(5.dp),
            color = primaryColor
        )
        Text(
            text = stringResource(R.string.gained_experience_points, exp),
            fontSize = 22.sp,
            modifier = Modifier.padding(5.dp),
            color = primaryColor
        )
        Button(
            onClick = {
                var currentExp = sharedPref?.getInt(SharedPreferencesKeys.playerExp, 0)
                if (currentExp != null) {
                    if (currentExp == 0) {
                        with(sharedPref?.edit()) {
                            this?.putInt(SharedPreferencesKeys.playerExp, exp)
                            this?.apply()
                        }
                    } else {
                        currentExp += exp
                        with(sharedPref?.edit()) {
                            this?.putInt(SharedPreferencesKeys.playerExp, currentExp)
                            this?.apply()
                        }
                    }
                }

                val currentHighScore = sharedPref?.getInt(SharedPreferencesKeys.playerHighScore, -1)
                
                if (score > currentHighScore!!) {
                    with(sharedPref.edit()) {
                        this?.putInt(SharedPreferencesKeys.playerHighScore, score)
                        this?.apply()
                    }
                }
                
                navController.navigate(Routes.menu)
            },
            modifier = Modifier.padding(5.dp),
        ) {
            Text(
                text = stringResource(R.string.go_to_menu),
                color = bg_color)
        }
    }
}

fun calculateScore(isGameWon: Boolean, hp: Int, attempts: Int, multiplayer: Int): Int {
    var tempScore = 0

    if (isGameWon) {
        tempScore += 50
    }

    tempScore += hp * multiplayer

    return tempScore - attempts / 2
}