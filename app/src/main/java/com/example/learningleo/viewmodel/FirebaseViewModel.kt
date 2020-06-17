package com.example.learningleo.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.example.learningleo.interactor.FirebaseIteractor
import kotlin.reflect.KFunction3

class FirebaseViewModel(val app: Application) : AndroidViewModel(app)
{
    private val iteractor = FirebaseIteractor(app.applicationContext)

    fun login(email: String, password: String, callback: (result: Int, text: String?, email: String) -> Unit)
    {
        iteractor.validateLogin(email,password,callback)
    }

    fun signUp(email: String, password: String,name: String,phone: String,confirmPassword: String, callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        iteractor.validateSignUp(email,password,name,phone,confirmPassword,callback)
    }

    fun resendPassword(email: String,callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        iteractor.validateResendPassword(email,callback)
    }

    fun logout(callback: () -> Unit)
    {
        iteractor.logout(callback)
    }




}