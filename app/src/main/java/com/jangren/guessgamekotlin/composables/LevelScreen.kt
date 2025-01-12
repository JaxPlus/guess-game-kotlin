package com.jangren.guessgamekotlin.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.ui.theme.dark_text_color
import com.jangren.guessgamekotlin.ui.theme.primaryColor

data class BubbleTea(
    val id: Int,
    val expToGet: Int,
    val title: String,
    val isObtained: Boolean
)

@Composable
fun LevelScreen(navController: NavController) {
    val context = LocalContext.current.findActivity()!!
    val sharedPref = context.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )
    
    val exp = sharedPref?.getInt(SharedPreferencesKeys.playerExp, 0) ?: 0
    
    val bubbleTeaList = remember {
        mutableStateListOf(
            BubbleTea(R.drawable.classic, 0, "Classic", true),
            BubbleTea(R.drawable.orange, 150, "Orange", exp >= 150),
            BubbleTea(R.drawable.choco, 280, "Choco", exp >= 280),
            BubbleTea(R.drawable.pink, 360, "Pink", exp >= 360),
            BubbleTea(R.drawable.mint, 480, "Mint", exp >= 480),
            BubbleTea(R.drawable.lime, 590, "Lime", exp >= 590),
            BubbleTea(R.drawable.lemon, 700, "Lemon", exp >= 700),
            BubbleTea(R.drawable.violet, 1000, "Violet", exp >= 1000),
            BubbleTea(R.drawable.unicorn, 1500, "Unicorn", exp >= 1500),
        )
    }
    
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        BtnToMenu(navController = navController)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bubble_tea_to_unlock),
                fontSize = 30.sp,
                color = primaryColor
            )
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(bubbleTeaList) { bubbleTea ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(bubbleTea.id),
                            contentDescription = context.getString(R.string.bubble_tea_to_get),
                            modifier = Modifier.scale(0.6F),
                            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { 
                                if (!bubbleTea.isObtained) {
                                    setToSaturation(0F)
                                }
                            })
                        )
                        Text(
                            text = context.getString(R.string.bubble_tea, bubbleTea.title),
                            color = primaryColor,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = context.getString(R.string.unlocked_at_exp, bubbleTea.expToGet.toString()),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        
                        if (!bubbleTea.isObtained) {
                            Text(
                                text = context.getString(
                                    R.string.exp_to_go,
                                    (bubbleTea.expToGet - exp).toString()
                                ),
                                modifier = Modifier.padding(bottom = 10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        else {
                            Text(
                                text = "",
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                        
                        OutlinedButton(
                            enabled = bubbleTea.isObtained,
                            onClick = {
                                Toast.makeText(context,
                                    context.getString(
                                        R.string.you_have_equipped_bubble_tea,
                                        bubbleTea.title
                                    ), Toast.LENGTH_SHORT).show()
                                
                                with(sharedPref?.edit()) {
                                    this?.putInt(SharedPreferencesKeys.bubbleTeaKey, bubbleTea.id)
                                    this?.apply()
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.equip),
                                color = dark_text_color
                            )
                        }
                    }
                }
            }
        }
    }
}