package com.example.learningleo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.learningleo.interactor.ChatBotIteractor

class ChatBotViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = ChatBotIteractor(app.applicationContext)


    fun send(text: String, callback : (reply:String?) -> Unit){
        interactor.send(text,callback)
    }
}