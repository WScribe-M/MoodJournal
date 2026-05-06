package com.example.moodjournal.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AIService {
    @POST("chat/completions")
    suspend fun getCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: ChatRequest
    ): ChatResponse
}