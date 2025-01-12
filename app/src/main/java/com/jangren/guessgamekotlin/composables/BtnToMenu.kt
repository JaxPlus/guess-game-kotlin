package com.jangren.guessgamekotlin.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jangren.guessgamekotlin.R
import com.jangren.guessgamekotlin.Routes
import com.jangren.guessgamekotlin.ui.theme.bg_color


@Composable
fun BtnToMenu(navController: NavController) {
    SmallFloatingActionButton(
        modifier = Modifier.padding(10.dp, 20.dp),
        onClick = { navController.navigate(Routes.menu) },
        shape = CircleShape
    ) {
        Icon(
            Icons.Filled.Close,
            tint = bg_color,
            modifier = Modifier.scale(1.15F),
            contentDescription = stringResource(R.string.back_button)
        )
    }
}