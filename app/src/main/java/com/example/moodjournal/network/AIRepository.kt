package com.example.moodjournal.network

import com.example.moodjournal.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// On construit l'instance Retrofit
class AIRepository {
    private val service = Retrofit.Builder()
        .baseUrl("https://router.huggingface.co/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AIService::class.java)

    suspend fun getAIReport(objectifs: List<String>, reponses: List<Int>): String {
        val prompt = """
            Tu es Lumi, un assistant bien-être bienveillant.
            L'utilisateur a répondu à un quiz de bien-être.
            Ses objectifs : ${objectifs.joinToString()}.
            Ses réponses (0=jamais, 3=presque toujours) : ${reponses.joinToString()}.
            Donne un bilan court et chaleureux (3-4 phrases) avec un conseil personnalisé.
        """.trimIndent()

        val response = service.getCompletion(
            apiKey = BuildConfig.HF_API_KEY,
            request = ChatRequest(
                model = "meta-llama/Llama-3.1-8B-Instruct",
                messages = listOf(Message(role = "user", content = prompt))
            )
        )
        return response.choices.first().message.content
    }

    suspend fun getCheckinResponse(emoji: String, intensity: Int, note: String): String {
        val prompt = """
            Tu es Lumi, un assistant bien être bienveillant.
            L'utilisateur fait son check-in du jour.
            Son émotion actuelle : $emoji.
            Son ressenti en intensité : $intensity/100.
            Ses notes : "$note".
            Fais lui un retour court et chaleureux en 2-3 phrases avec un conseil personnalisé.
        """.trimIndent()

        val response = service.getCompletion(
            apiKey = BuildConfig.HF_API_KEY,
            request = ChatRequest(
                model = "meta-llama/Llama-3.1-8B-Instruct",
                messages = listOf(Message(role = "user", content = prompt))
            )
        )
        return response.choices.first().message.content
    }
}