package com.example.exam2024.Data

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

fun loadGameData(context: Context): GameData {
    val gson = Gson()
    val inputStream = context.assets.open("secuencias.json")
    val reader = InputStreamReader(inputStream)
    return gson.fromJson(reader, GameData::class.java)
}
