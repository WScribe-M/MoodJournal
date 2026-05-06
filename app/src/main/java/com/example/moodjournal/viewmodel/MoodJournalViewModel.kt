package com.example.moodjournal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodjournal.network.AIRepository
import kotlinx.coroutines.launch

class MoodJournalViewModel : ViewModel() {
    private val AIRepository = AIRepository()

    var objectifs by mutableStateOf(mutableListOf<String>())
       /* Seul le ViewModel peut modifier les valeurs, pas les écrans directement.
        - Ils passent par les fonctions updates.*/
        private set
    var reponses by mutableStateOf(mutableListOf<Int>())
        private set
    var aiReport by mutableStateOf("")
        private set


    fun updateObjectifs(newObjectifs: MutableList<String>) {
        objectifs = newObjectifs
        /*Log.d("ViewModel", "Objectifs : $objectifs")*/
    }

    fun updateReponses(newReponses: MutableList<Int>) {
        reponses = newReponses
        /*Log.d("ViewModel", "Reponses : $reponses")*/
    }

    fun generateReport() {
        viewModelScope.launch {
            aiReport = AIRepository.getAIReport(objectifs, reponses)
        }
    }
}