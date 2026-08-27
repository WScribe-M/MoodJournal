package com.example.moodjournal.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodjournal.data.local.UserStorage
import com.example.moodjournal.data.model.User
import com.example.moodjournal.network.AIRepository
import kotlinx.coroutines.launch
import java.io.IOException

private const val TAG = "MoodJournalViewModel"

class MoodJournalViewModel(application: Application) : AndroidViewModel(application) {

    private val userStorage   = UserStorage(application)
    private val AIRepository  = AIRepository()

    var reponses by mutableStateOf(mutableListOf<Int>())
        private set

    var aiReport by mutableStateOf("")
        private set

    var reportLoading by mutableStateOf(false)
        private set

    var reportError by mutableStateOf<String?>(null)
        private set

    var checkinEmotion by mutableStateOf("")
        private set

    var checkinIntensity by mutableStateOf(0)
        private set

    var checkinNote by mutableStateOf("")
        private set

    var aiCheckinResponse by mutableStateOf("")
        private set

    var checkinLoading by mutableStateOf(false)
        private set

    var checkinError by mutableStateOf<String?>(null)
        private set

    fun updateReponses(newReponses: MutableList<Int>) {
        reponses = newReponses
    }

    fun generateReport() {
        if (reportLoading || aiReport.isNotEmpty()) return
        val user = userStorage.getUser()
        val userObjectifs = user?.objectifs ?: emptyList()
        reportLoading = true
        reportError   = null
        viewModelScope.launch {
            try {
                aiReport = AIRepository.getAIReport(userObjectifs, reponses)
            } catch (e: Exception) {
                Log.e(TAG, "Échec de génération du bilan", e)
                reportError = messageFor(e)
            } finally {
                reportLoading = false
            }
        }
    }

    fun retryReport() {
        reportError = null
        generateReport()
    }

    fun updateCheckin(emotion: String, intensity: Int, note: String) {
        checkinEmotion   = emotion
        checkinIntensity = intensity
        checkinNote      = note
    }

    fun generateCheckinResponse() {
        if (checkinLoading || aiCheckinResponse.isNotEmpty()) return
        checkinLoading = true
        checkinError   = null
        viewModelScope.launch {
            try {
                aiCheckinResponse = AIRepository.getCheckinResponse(
                    checkinEmotion,
                    checkinIntensity,
                    checkinNote,
                )
            } catch (e: Exception) {
                Log.e(TAG, "Échec de génération de la réponse check-in", e)
                checkinError = messageFor(e)
            } finally {
                checkinLoading = false
            }
        }
    }

    fun retryCheckinResponse() {
        checkinError = null
        generateCheckinResponse()
    }

    /** Message lisible pour l'utilisateur, à afficher à la place du crash. */
    private fun messageFor(e: Exception): String = when (e) {
        is IOException -> "Lumi n'arrive pas à se connecter. Vérifie ta connexion et réessaie."
        else           -> "Lumi n'a pas réussi à préparer ton bilan. Réessaie dans un instant."
    }

    fun saveUser(nom: String, prenom: String, email: String, sexe: String) {
        userStorage.saveUser(User(nom, prenom, email, sexe))
    }

    fun getUser(): User? = userStorage.getUser()

    fun clearUser() = userStorage.clearUser()

    fun setConsentGiven(given: Boolean) = userStorage.setConsentGiven(given)

    fun hasConsent(): Boolean = userStorage.hasConsent()

    fun updateUserObjectifs(objectifs: List<String>) {
        val current = userStorage.getUser() ?: return
        userStorage.saveUser(current.copy(objectifs = objectifs))
    }
}
