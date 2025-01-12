package com.jangren.guessgamekotlin.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.Routes
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.gamefunctionality.Game
import com.jangren.guessgamekotlin.gamefunctionality.GameState
import com.jangren.guessgamekotlin.gamefunctionality.Player
import com.jangren.guessgamekotlin.ui.theme.primaryColor


@Composable
fun GameScreen(player: Player, navController: NavController) {
    val context = LocalContext.current.findActivity()!!
    var guess by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var btnText by remember { mutableIntStateOf(R.string.check) }
    val game by remember { mutableStateOf(Game(player, context)) }
    val lifeLeft = remember {
        mutableStateListOf<Int>()
    }

    val sharedPref = context.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )
    
    val bubbleTea = sharedPref.getInt(SharedPreferencesKeys.bubbleTeaKey, R.drawable.classic)
    
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        for (i in 1 .. player.hp) {
            if (lifeLeft.size < player.hp)
            {
                lifeLeft.add(bubbleTea)
            }
        }
        
        BtnToMenu(navController = navController)
        Text(
            text = stringResource(R.string.numbers_between) + player.getRange(),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            color = primaryColor
        )
        LazyVerticalGrid(columns = GridCells.Fixed(if (player.hp != 0) player.hp else 1), modifier = Modifier.height(150.dp)) {
            items(player.hp) { index ->
                Image(
                    painter = painterResource(lifeLeft[index]),
                    contentDescription = stringResource(R.string.your_hp_but_boba_tea),
                    modifier = Modifier.scale(0.6F)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = result,
                fontSize = 38.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp, top = 20.dp),
                color = primaryColor
            )
            OutlinedTextField(
                value = guess,
                enabled = !game.isGameEnded,
                onValueChange = {
                    guess = it.filter { c ->
                        when (c) {
                            in "qwertyuiopasdfghjklzxcvbnm!@#$%^&*()~:/\"\\,.<>;'[]{}|-=_+ \n" -> false
                            else -> true
                        }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.enter_a_number),
                        color = primaryColor
                    )
                },
                textStyle = TextStyle(color = primaryColor),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    if (btnText == R.string.go_to_stats) {
                        val gameState = GameState(
                            attempts = game.attempts,
                            hpLeft = player.hp,
                            isGameWon = result == context.getString(R.string.you_won),
                            difficulty = player.difficulty
                        )
                        
                        navController.navigate(Routes.GameEndScreenRoute(gameState))
                        return@OutlinedButton
                    }
                    
                    if (guess.isEmpty()) {
                        Toast.makeText(context,
                            context.getString(R.string.please_input_a_number), Toast.LENGTH_SHORT).show()
                        return@OutlinedButton
                    }
                    
                    result = game.guessNumber(guess.toInt())

                    when (result) {
                        context.getString(R.string.your_guess_is_lower), context.getString(R.string.your_guess_is_higher) -> {
                            lifeLeft.removeLast()
                            player.loseHp()
                        }
                        context.getString(R.string.you_won) -> {
                            btnText = R.string.go_to_stats
                        }
                        context.getString(R.string.you_lost) -> {
                            btnText = R.string.go_to_stats
                            lifeLeft.removeLast()
                            player.loseHp()
                        }
                    }
                    
                    guess = ""
                }
            ) {
                Text(text = stringResource(btnText))
            }
        }
    }
}