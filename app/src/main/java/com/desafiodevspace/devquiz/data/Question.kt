package com.desafiodevspace.devquiz.data

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)