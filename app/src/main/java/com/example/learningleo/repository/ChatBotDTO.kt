package com.example.learningleo.repository

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class ChatResponseDTO(
    @SerializedName("queryResult")
    val result: ChatResponseDTO2? = null
)
data class ChatResponseDTO2(
    @SerializedName("fulfillmentMessages")
    val result: ArrayList<ChatResponseDTO3>? = null
)
data class ChatResponseDTO3(
    @SerializedName("text")
    val result: ChatResponseDTO4? = null
)
data class ChatResponseDTO4(
    @SerializedName("text")
    val result: ArrayList<String>? = null
)

data class ResponseBody(
    val text: String,
    val email: String,
    val sessionId: String
)