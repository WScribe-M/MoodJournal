package com.example.moodjournal.data.local

import android.content.Context
import com.example.moodjournal.data.model.CheckIn
import com.example.moodjournal.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserStorage (context: Context){
    private val prefs = context.getSharedPreferences("moodjournal", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "users"

    fun saveUser(user: User){
        val json = gson.toJson(user)
        prefs.edit().putString(key, json).apply()
    }

    fun getUser(): User? {
        val json = prefs.getString(key, null) ?: return null
        return gson.fromJson(json, User::class.java)
    }

    fun clearUser(){
        prefs.edit().remove(key).apply()
    }
}