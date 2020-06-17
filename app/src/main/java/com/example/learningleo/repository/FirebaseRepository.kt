package com.example.learningleo.repository

import android.app.Activity.RESULT_OK
import android.content.Intent
import com.example.learningleo.domain.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseRepository() {
    val mAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val RESULT_ERROR = 0
    val RESULT_INFO = 1

    fun login(email: String, password: String, callback: (result: Int, text: String?,email:String) -> Unit) {
        val operation = mAuth.signInWithEmailAndPassword(email, password)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                getUserName(email,callback)
            } else {
                val error = task.exception?.localizedMessage
                    ?: "Unable to login, try again later."
                callback(RESULT_ERROR, error,"")
            }
        }
    }

    fun signUp(email: String, password: String, name: String, phone: String, callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        val operation = mAuth.createUserWithEmailAndPassword(email,password)
        operation.addOnCompleteListener{task ->
            if(task.isSuccessful)
            {
                saveUser(email,name,phone,callback)
                verifyEmail(callback)

                val intentResult= Intent()
                intentResult.putExtra("Email",email)
                intentResult.putExtra("Password",password)
                callback(RESULT_OK,"User registered successfully!",intentResult)
            }
            else
            {
                val error = task.exception?.localizedMessage ?: "Unable to register, try again later."
                callback(RESULT_ERROR,error,null)
            }
        }
    }

    fun resendPassword(email: String,callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit)
    {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    val intentResult = Intent()
                    intentResult.putExtra("Email",email)
                    callback(RESULT_OK,"An email was sent to reset your password.",intentResult)
                }
                else
                {
                    val error = task.exception?.localizedMessage ?: "Unable to send email."
                    callback(RESULT_ERROR,error,null)
                }
            }
    }

    fun logout(callback: () -> Unit)
    {
        mAuth.signOut()
        callback()
    }

    private fun saveUser(email: String, name: String, phone: String, callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit) {
        val profile = Profile(email, name, phone)

        val uid = mAuth.currentUser?.uid

        if(uid != null)
        {
            val userProfile = database.getReference("profile/$uid")
            userProfile.setValue(profile)
        }
        else
        {
            callback(RESULT_INFO,"Unable to retrieve user id.",null)
        }
    }

    private fun verifyEmail(callback: (result: Int, text: String?, resultIntend: Intent?) -> Unit) {
        val currentUser = mAuth.currentUser
        if (currentUser != null)
        {
            currentUser.sendEmailVerification()
                .addOnCompleteListener{task ->
                    if(task.isSuccessful)
                    {
                        callback(RESULT_INFO,"Email verification sent to ${currentUser.email}",null)
                    }
                    else
                    {
                        val error = task.exception?.localizedMessage ?: "Unable to send verification email."
                        callback(RESULT_INFO,error,null)
                    }
                }
        }
        else{ callback(RESULT_INFO,"Unable to find user.",null)}
    }

    private fun getUserName(userEmail:String,callback: (result: Int, text: String?,email: String) -> Unit) {
        val userId = mAuth.currentUser!!.uid
        database.getReference("profile/$userId")
            .addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(Profile::class.java)
                val userName = user!!.name.toString()
                callback(RESULT_OK,userName,userEmail)
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}