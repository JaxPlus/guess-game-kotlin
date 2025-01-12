package com.jangren.guessgamekotlin.composables

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.SharedPreferencesKeys
import com.jangren.guessgamekotlin.findActivity
import com.jangren.guessgamekotlin.ui.theme.GuessGameKotlinTheme
import com.jangren.guessgamekotlin.ui.theme.bg_color
import com.jangren.guessgamekotlin.ui.theme.primaryColor

@Composable
fun AlertDialogNickForm(shouldShowDialog: MutableState<Boolean>) {
    if (!shouldShowDialog.value) {
        return
    }
    
    var userNick by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current.findActivity()!!
    val sharedPref = context.getSharedPreferences(
        SharedPreferencesKeys.sharedPreferencesKey, Context.MODE_PRIVATE
    )
    
    Dialog(
        onDismissRequest = { null },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(bg_color)
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bg_color)
            ) {
                Text(
                    text = stringResource(R.string.your_nick),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = primaryColor
                )
                OutlinedTextField(value = userNick, 
                    modifier = Modifier
                        .padding(10.dp),
                    textStyle = TextStyle(color = primaryColor),
                    label = {
                        Text(
                            text = stringResource(R.string.enter_your_nick),
                            color = primaryColor
                        )
                    },
                    onValueChange = { userNick = it }
                )
                Button(onClick = {
                    if (userNick.isEmpty()) {
                        Toast.makeText(context,
                                context.getString(R.string.please_enter_your_nick), Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    with(sharedPref?.edit()) {
                        this?.putString(SharedPreferencesKeys.playerNick, userNick)
                        this?.apply()
                    }
                    
                    shouldShowDialog.value = false
                }) {
                    Text(text = stringResource(R.string.accept), color = bg_color)
                }
            }
        }
    }
}