package com.jangren.guessgamekotlin.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.Routes
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.ui.theme.primaryColor

@Composable
fun MenuScreen(navController: NavController) {
    val context = LocalContext.current.findActivity()
    val sharedPref = context?.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )

    val bubbleTea = sharedPref?.getInt(
        SharedPreferencesKeys.bubbleTeaKey,
        R.drawable.classic) ?: R.drawable.classic
    
    val shouldDialogShow = remember {
        mutableStateOf(sharedPref?.getString(SharedPreferencesKeys.playerNick, "") == "")
    }
    
    if (shouldDialogShow.value) {
        AlertDialogNickForm(shouldDialogShow)
    }
    
    Text(
        text = stringResource(R.string.guess_the_number_game),
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 40.dp),
        color = primaryColor,
        textAlign = TextAlign.Center,
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick = { 
                navController.navigate(Routes.gameDifficulty)
            },
            modifier = Modifier
                .padding(bottom = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.play),
                fontSize = 24.sp,
                modifier = Modifier.padding(40.dp, 8.dp)
            )
        }
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.level)
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = stringResource(R.string.star_icon),
                    modifier = Modifier
                        .scale(1.15F)
                )
                Text(
                    text = stringResource(R.string.levels),
                    modifier = Modifier.padding(40.dp, 8.dp)
                )
            }
        }
        OutlinedButton(
            onClick = { 
                navController.navigate(Routes.leaderboard)
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                painterResource(R.drawable.baseline_leaderboard_24),
                contentDescription = stringResource(R.string.leaderboard_icon),
                modifier = Modifier
                    .scale(1.15F)
            )
            Text(
                text = stringResource(R.string.leaderboard),
                modifier = Modifier.padding(40.dp, 8.dp)
            )
        }
        OutlinedButton(
            onClick = { context?.finish() },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.quit),
                modifier = Modifier.padding(40.dp, 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = bubbleTea),
            contentDescription = stringResource(R.string.your_companion_bubble_tea)
        )
    }
}