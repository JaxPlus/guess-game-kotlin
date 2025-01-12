package com.jangren.guessgamekotlin.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.ui.theme.bg_color
import com.jangren.guessgamekotlin.ui.theme.primaryColor

@Composable
fun LeaderboardScreen(navController: NavController) {
    val context = LocalContext.current.findActivity()!!
    val sharedPref = context.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )
    
    var playerNick by remember {
        mutableStateOf(sharedPref?.getString(SharedPreferencesKeys.playerNick,
            context.getString(R.string.none)) ?: context.getString(R.string.none))
    }
    var newNick by remember {
        mutableStateOf(playerNick)
    }
    val highScore = sharedPref?.getInt(SharedPreferencesKeys.playerHighScore, 0) ?: 0
    val exp = sharedPref?.getInt(SharedPreferencesKeys.playerExp, 0) ?: 0
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        BtnToMenu(navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = playerNick,
                fontSize = 36.sp,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = stringResource(R.string.highscore_is, highScore),
                fontSize = 26.sp,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = stringResource(R.string.your_experience_points, exp),
                fontSize = 24.sp,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            OutlinedTextField(
                value = newNick,
                textStyle = TextStyle(color = primaryColor),
                label = {
                    Text(
                        text = stringResource(R.string.change_your_nick),
                        color = primaryColor
                    )
                },
                modifier = Modifier.padding(bottom = 20.dp),
                onValueChange = { newNick = it }
            )
            Button(onClick = {
                if (newNick.isEmpty()) {
                    Toast.makeText(context, context.getString(R.string.please_enter_your_nick), Toast.LENGTH_SHORT).show()
                    return@Button
                }

                with(sharedPref?.edit()) {
                    this?.putString(SharedPreferencesKeys.playerNick, newNick)
                    this?.apply()
                }

                playerNick = newNick
            }) {
                Text(
                    text = stringResource(R.string.set_your_new_nick),
                    color = bg_color
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}