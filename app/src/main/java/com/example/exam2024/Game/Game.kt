package com.example.exam2024.Game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.exam2024.Data.SequenceData
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(timeLimit: Int, onTimeUp: () -> Unit) {
    var timeLeft by remember { mutableStateOf(timeLimit) }

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else {
            onTimeUp()
        }
    }

    Text(text = "Tiempo restante: $timeLeft segundos")
}

fun checkAnswers(sequenceData: SequenceData, userAnswers: List<Int>): Boolean {
    return sequenceData.answers == userAnswers
}

