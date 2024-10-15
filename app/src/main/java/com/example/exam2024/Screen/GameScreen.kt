package com.example.exam2024.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exam2024.Data.Level
import com.example.exam2024.Data.SequenceData
import com.example.exam2024.Game.TimerScreen

@Composable
fun GameScreen(level: Level, onLevelUp: () -> Unit, onGameOver: () -> Unit) {
    var currentTurn by remember { mutableStateOf(0) }
    var attemptsLeft by remember { mutableStateOf(5) }
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    // Lista para capturar las respuestas del usuario solo para los valores faltantes
    val userAnswers = remember { mutableStateListOf<Int?>() }

    val sequenceList = level.sequences
    val currentSequenceData = sequenceList[currentTurn]

    // Temporizador
    TimerScreen(timeLimit = 60) {
        attemptsLeft--
        message = "¡Se acabó el tiempo!"
        showMessage = true
        if (attemptsLeft == 0) {
            onGameOver()
        }
    }

    Column {
        Text(text = "Nivel ${level.level}")
        Column {
            Text(text = "Intentos restantes: $attemptsLeft")
        }


        // Mostrar la secuencia actual en el turno correspondiente
        SequenceRow(currentSequenceData, userAnswers)

        if (showMessage) {
            Text(text = message, color = Color.Red)
        }

        // Botón para comprobar la respuesta
        Button(onClick = {
            // Verificar las respuestas ingresadas por el usuario
            if (checkAnswers(currentSequenceData, userAnswers)) {
                message = "¡Correcto!"
                showMessage = true
                if (currentTurn < 2) {
                    currentTurn++
                    userAnswers.clear() // Limpiar las respuestas para el siguiente turno
                } else {
                    onLevelUp()
                }
            } else {
                attemptsLeft--
                message = "Respuesta incorrecta"
                showMessage = true
                if (attemptsLeft == 0) {
                    onGameOver()
                }
            }
        }) {
            Text("Comprobar")
        }
    }
}


@Composable
fun SequenceRow(sequenceData: SequenceData, userAnswers: MutableList<Int?>) {
    Row {
        // Asegúrate de que userAnswers tenga el tamaño adecuado para capturar todas las respuestas faltantes
        if (userAnswers.size != sequenceData.missingCount) {
            userAnswers.clear()
            repeat(sequenceData.missingCount) {
                userAnswers.add(null) // Inicializar con null para cada valor faltante
            }
        }

        sequenceData.sequence.forEachIndexed { index, item ->
            if (item == null) {
                // Capturar el índice correspondiente a este campo de respuesta
                val answerIndex = sequenceData.missingIndices.indexOf(index)
                var answer by remember { mutableStateOf("") }

                TextField(
                    value = answer,
                    onValueChange = { newValue ->
                        answer = newValue
                        // Verificar que sea un número y guardarlo en el índice correcto
                        val parsedAnswer = newValue.toIntOrNull()
                        if (parsedAnswer != null && answerIndex >= 0) {
                            userAnswers[answerIndex] = parsedAnswer
                        }
                    },
                    modifier = Modifier.width(40.dp)
                )
            } else {
                // Mostrar los elementos de la secuencia que no están vacíos
                Text(text = item.toString(), modifier = Modifier.padding(8.dp))
            }
        }
    }
}

fun checkAnswers(sequenceData: SequenceData, userAnswers: List<Int?>): Boolean {
    // Convertir las respuestas del usuario a una lista no nula y compararlas con las respuestas correctas
    return userAnswers.filterNotNull() == sequenceData.answers
}
