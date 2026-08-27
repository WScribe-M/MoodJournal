package com.example.moodjournal.network

import com.example.moodjournal.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// On construit l'instance Retrofit
class AIRepository {
    // Les modèles Hugging Face ont un démarrage à froid qui dépasse
    // largement les 10 s de timeout par défaut d'OkHttp.
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .callTimeout(120, TimeUnit.SECONDS)
        .build()

    private val service = Retrofit.Builder()
        .baseUrl("https://router.huggingface.co/v1/")
        .client(client)
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

        return complete(prompt)
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

        return complete(prompt)
    }

    private suspend fun complete(prompt: String): String {
        val response = service.getCompletion(
            apiKey = BuildConfig.HF_API_KEY,
            request = ChatRequest(
                model = "meta-llama/Llama-3.1-8B-Instruct",
                messages = listOf(Message(role = "user", content = prompt))
            )
        )
        val content = response.choices?.firstOrNull()?.message?.content?.trim()
        // Une réponse vide n'est pas une erreur réseau : on remonte quand même
        // un échec pour que l'écran propose de réessayer plutôt que de tourner
        // indéfiniment sur son indicateur de chargement.
        if (content.isNullOrEmpty()) error("Réponse vide du modèle")
        return content
    }
}
