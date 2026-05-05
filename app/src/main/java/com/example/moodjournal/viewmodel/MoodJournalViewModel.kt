package com.example.moodjournal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MoodJournalViewModel : ViewModel() {
    var objectifs by mutableStateOf(mutableListOf<String>())
       /* Seul le ViewModel peut modifier les valeurs, pas les écrans directement.
        - Ils passent par les fonctions updates.*/
        private set
    var reponses by mutableStateOf(mutableListOf<Int>())
        private set

    fun updateObjectifs(newObjectifs: MutableList<String>) {
        objectifs = newObjectifs
    }

    fun updateReponses(newReponses: MutableList<Int>) {
        reponses = newReponses
    }
}