package com.example.moodjournal.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodjournal.data.local.CheckinStorage
import com.example.moodjournal.data.local.GratitudeStorage
import com.example.moodjournal.data.local.UserStorage
import com.example.moodjournal.data.model.CheckIn
import com.example.moodjournal.data.model.Gratitude
import com.example.moodjournal.data.model.User
import com.example.moodjournal.network.AIRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class MoodJournalViewModel(application: Application) : AndroidViewModel(application) {

    private val storage = CheckinStorage(application)
    private val storageGratitude = GratitudeStorage(application)

    private val userStorage = UserStorage(application)
    private val AIRepository = AIRepository()

    /*var objectifs by mutableStateOf(mutableListOf<String>())*/
        /*private set*/

    /* Seul le ViewModel peut modifier les valeurs, pas les écrans directement.
       - Ils passent par les fonctions updates.*/
    var reponses by mutableStateOf(mutableListOf<Int>())
        private set
    var aiReport by mutableStateOf("")
        private set

    var name by mutableStateOf("Emma")
        private set

    var checkinEmotion by mutableStateOf("")
        private set

    var checkinIntensity by mutableStateOf(0)
        private set

    var checkinNote by mutableStateOf("")
        private set

    var aiCheckinResponse by mutableStateOf("")
        private set

    var gratitudeResponse by mutableStateOf(List(5) { "" })
        private set

/*    fun updateObjectifs(newObjectifs: MutableList<String>) {
        objectifs = newObjectifs
        *//*Log.d("ViewModel", "Objectifs : $objectifs")*//*
    }*/

    fun updateReponses(newReponses: MutableList<Int>) {
        reponses = newReponses
        /*Log.d("ViewModel", "Reponses : $reponses")*/
    }

    fun generateReport() {
        val user = userStorage.getUser()
        val userObjectifs = user?.objectifs ?: emptyList()
        viewModelScope.launch {
            aiReport = AIRepository.getAIReport(userObjectifs, reponses)
        }
    }

    fun updateName(newName: String){
        name = newName
    }

    fun updateCheckin(emotion: String, intensity: Int, note: String) {
        checkinEmotion = emotion
        checkinIntensity = intensity
        checkinNote = note
    }

    fun generateCheckinResponse(){
        viewModelScope.launch {
            aiCheckinResponse = AIRepository.getCheckinResponse(
                checkinEmotion,
                checkinIntensity,
                checkinNote
            )
            saveCheckin()
        }
    }

    //Sauvegarde le check-in courant
    fun saveCheckin() {
        val checkin = CheckIn(
            emotion = checkinEmotion,
            intensity = checkinIntensity,
            note = checkinNote,
            date = LocalDate.now().toString()
        )
        storage.saveCheckin(checkin)
    }

    fun getCheckins(): List<CheckIn> = storage.getCheckins()

    fun saveGratitude(notes: List<String>) {
        val gratitude = Gratitude(
            notes = notes,
            date = LocalDate.now().toString()
        )
        storageGratitude.saveGratitude(gratitude)
    }

    fun getGratitude(): List<Gratitude> = storageGratitude.getGratitude()

    fun saveUser(nom: String, prenom: String, email: String, sexe: String) {
        val user = User(nom, prenom, email, sexe)
        userStorage.saveUser(user)
    }

    fun getUser(): User? = userStorage.getUser()

    fun clearUser() = userStorage.clearUser()

    fun updateUserObjectifs(objectifs: List<String>) {
        val currentUser = userStorage.getUser() ?: return
        val updated = currentUser.copy(objectifs = objectifs)
        userStorage.saveUser(updated)
    }
}
