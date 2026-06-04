package com.example.moodjournal.data.local

import android.content.Context
import com.example.moodjournal.data.model.CheckIn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// manager qui s'occupe de sauvegarder et lire les check-ins.
class CheckinStorage (context: Context) {
    private val prefs = context.getSharedPreferences("moodjournal", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "checkins"

    fun saveCheckin(checkin: CheckIn) {
        val current = getCheckins().toMutableList()
        current.add(checkin)
        val json = gson.toJson(current)
        prefs.edit().putString(key, json).apply()
    }

    fun getCheckins(): List<CheckIn> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<CheckIn>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearCheckins() {
        prefs.edit().remove(key).apply()
    }
}