package com.example.moodjournal.data.local

import android.content.Context
import com.example.moodjournal.data.model.Gratitude
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GratitudeStorage(context: Context) {
    private val prefs = context.getSharedPreferences("moodjournal", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "gratitudes"

    fun saveGratitude(gratitude: Gratitude) {
        val current = getGratitude().toMutableList()
        current.add(gratitude)
        val json = gson.toJson(current)
        prefs.edit().putString(key, json).apply()
    }

    fun getGratitude(): List<Gratitude> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Gratitude>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearGratitude() {
        prefs.edit().remove(key).apply()
    }
}