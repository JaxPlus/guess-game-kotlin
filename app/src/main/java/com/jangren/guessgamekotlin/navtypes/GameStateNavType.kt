package com.jangren.guessgamekotlin.navtypes

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.jangren.guessgamekotlin.gamefunctionality.GameState
import com.jangren.guessgamekotlin.gamefunctionality.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object GameStateNavType {
    val gameStateType = object : NavType<GameState>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): GameState? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): GameState {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: GameState): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: GameState) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}