package com.jangren.guessgamekotlin.navtypes

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.jangren.guessgamekotlin.gamefunctionality.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PlayerNavType {
    val playerType = object : NavType<Player>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Player? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Player {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Player): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Player) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}