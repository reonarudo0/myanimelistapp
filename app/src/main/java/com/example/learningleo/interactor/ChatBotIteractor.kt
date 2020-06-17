package com.example.learningleo.interactor

import android.content.Context
import android.util.Log
import com.example.learningleo.repository.ChatBotRepository
import java.security.AccessControlContext

class ChatBotIteractor(context: Context){
    val BASE_URL = "https://leo-dialog-flow.herokuapp.com/"
    private val repository = ChatBotRepository(context,BASE_URL)

    fun send(text: String, callback : (reply:String?) -> Unit)
    {
        repository.send(text,callback)
    }

}