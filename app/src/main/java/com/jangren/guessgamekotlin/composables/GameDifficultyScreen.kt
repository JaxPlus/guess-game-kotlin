package com.jangren.guessgamekotlin.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.Routes
import com.jangren.guessgamekotlin.gamefunctionality.GameDifficulty
import com.jangren.guessgamekotlin.gamefunctionality.Player
import com.jangren.guessgamekotlin.ui.theme.bg_color
import com.jangren.guessgamekotlin.ui.theme.primaryColor


@Composable
fun GameDifficultyView(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        BtnToMenu(navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.select_difficulty),
                fontSize = 30.sp,
                color = primaryColor
            )
            Button(
                onClick = { 
                    val player = Player(GameDifficulty.EASY)
                    
                    navController.navigate(
                        Routes.GameScreenRoute(player)
                    )
                }) {
                Text(
                    text = stringResource(R.string.easy),
                    color = bg_color
                )
            }
            Button(
                onClick = {
                    val player = Player(GameDifficulty.MEDIUM)

                    navController.navigate(
                        Routes.GameScreenRoute(player)
                    )}) {
                Text(
                    text = stringResource(R.string.medium),
                    color = bg_color
                )
            }
            Button(
                onClick = {
                    val player = Player(GameDifficulty.HARD)

                    navController.navigate(
                        Routes.GameScreenRoute(player)
                    )
                }) {
                Text(
                    text = stringResource(R.string.hard),
                    color = bg_color
                )
            }
        }
    }
}