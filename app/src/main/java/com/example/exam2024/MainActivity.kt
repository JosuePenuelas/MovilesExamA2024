package com.example.exam2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.exam2024.Data.GameData
import com.example.exam2024.Screen.GameScreen
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar el archivo JSON
        val gameData = loadGameData()

        setContent {
            var currentLevel by remember { mutableStateOf(0) }

            GameScreen(
                level = gameData.levels[currentLevel],
                onLevelUp = {
                    currentLevel++
                    // Avanzar al siguiente nivel
                },
                onGameOver = {
                    // Lógica para cuando se acaben los intentos (puedes mostrar una pantalla de fin)
                }
            )
        }
    }

    private fun loadGameData(): GameData {
        val gson = Gson()
        val inputStream = assets.open("secuencias.json")
        val reader = InputStreamReader(inputStream)
        return gson.fromJson(reader, GameData::class.java)
    }
}

