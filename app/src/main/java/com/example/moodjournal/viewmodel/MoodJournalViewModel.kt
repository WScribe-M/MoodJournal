package com.example.moodjournal.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodjournal.data.local.UserStorage
import com.example.moodjournal.data.model.User
import com.example.moodjournal.network.AIRepository
import kotlinx.coroutines.launch

class MoodJournalViewModel(application: Application) : AndroidViewModel(application) {

    private val userStorage   = UserStorage(application)
    private val AIRepository  = AIRepository()

    var reponses by mutableStateOf(mutableListOf<Int>())
        private set

    var aiReport by mutableStateOf("")
        private set

    var checkinEmotion by mutableStateOf("")
        private set

    var checkinIntensity by mutableStateOf(0)
        private set

    var checkinNote by mutableStateOf("")
        private set

    var aiCheckinResponse by mutableStateOf("")
        private set

    fun updateReponses(newReponses: MutableList<Int>) {
        reponses = newReponses
    }

    fun generateReport() {
        val user = userStorage.getUser()
        val userObjectifs = user?.objectifs ?: emptyList()
        viewModelScope.launch {
            aiReport = AIRepository.getAIReport(userObjectifs, reponses)
        }
    }

    fun updateCheckin(emotion: String, intensity: Int, note: String) {
        checkinEmotion   = emotion
        checkinIntensity = intensity
        checkinNote      = note
    }

    fun generateCheckinResponse() {
        viewModelScope.launch {
            aiCheckinResponse = AIRepository.getCheckinResponse(
                checkinEmotion,
                checkinIntensity,
                checkinNote,
            )
        }
    }

    fun saveUser(nom: String, prenom: String, email: String, sexe: String) {
        userStorage.saveUser(User(nom, prenom, email, sexe))
    }

    fun getUser(): User? = userStorage.getUser()

    fun clearUser() = userStorage.clearUser()

    fun updateUserObjectifs(objectifs: List<String>) {
        val current = userStorage.getUser() ?: return
        userStorage.saveUser(current.copy(objectifs = objectifs))
    }
}
