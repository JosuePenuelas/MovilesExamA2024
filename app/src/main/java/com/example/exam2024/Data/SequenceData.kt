package com.example.exam2024.Data

data class SequenceData(
    val sequence: List<Int?>,
    val missingCount: Int,
    val missingIndices: List<Int>,
    val answers: List<Int>
)

data class Level(
    val level: Int,
    val sequences: List<SequenceData>
)

data class GameData(
    val levels: List<Level>
)
