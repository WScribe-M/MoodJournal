package com.example.moodjournal.network

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

// Côté réponse, Gson ignore les valeurs par défaut de Kotlin et peut laisser
// ces champs à null si le JSON renvoyé par l'API n'a pas la forme attendue
// (erreur, quota dépassé…). On les déclare nullables pour éviter un NPE.
data class ChatResponse(
    val choices: List<Choice>?
)

data class Choice(
    val message: ResponseMessage?
)

data class ResponseMessage(
    val role: String?,
    val content: String?
)
