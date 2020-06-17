package com.example.learningleo.interactor

import android.content.Context
import android.content.Intent
import com.example.learningleo.repository.FirebaseRepository


class FirebaseIteractor(private val context: Context){
    private val firebaseRepository = FirebaseRepository()
    private val RESULT_ERROR: Int = 0

    fun validateLogin(email: String,password: String,callback: (result: Int,text: String?,email:String)-> Unit ) {
        if(email.isEmpty())
        {
            callback(RESULT_ERROR, "The email is required!","")
            return
        }
        if(password.isEmpty())
        {
            callback(RESULT_ERROR,"The password is required!","")
            return
        }
        if(password.length < 6)
        {
            callback(RESULT_ERROR,"The password must contain at least six characters!","")
            return
        }
        firebaseRepository.login(email,password,callback)

    }

    fun validateSignUp(email: String, password: String,name: String,phone: String,confirmPassword: String, callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        if(email.isEmpty() or password.isEmpty() or name.isEmpty() or phone.isEmpty() or confirmPassword.isEmpty())
        {
            callback(RESULT_ERROR, "Please fill all required fields.",null)
            return
        }
        if(password.length < 6)
        {
            callback(RESULT_ERROR,"The password must contain at least six characters!",null)
            return
        }
        if(!password.equals(confirmPassword))
        {
            callback(RESULT_ERROR,"Passwords don't match!",null)
            return
        }
        firebaseRepository.signUp(email,password,name,phone,callback)
    }

    fun validateResendPassword(email: String,callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        if(email.isEmpty())
        {
            callback(RESULT_ERROR,"Please fill all required fields.",null)
            return
        }
        firebaseRepository.resendPassword(email,callback)
    }

    fun logout(callback: () -> Unit)
    {
        firebaseRepository.logout(callback)
    }
}