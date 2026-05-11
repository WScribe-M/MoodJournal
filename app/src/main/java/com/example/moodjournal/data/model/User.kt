package com.example.moodjournal.data.model

data class User (
    val nom: String,
    val prenom: String,
    val email: String,
    val sexe: String,
    val objectifs: List<String>? = emptyList()
)