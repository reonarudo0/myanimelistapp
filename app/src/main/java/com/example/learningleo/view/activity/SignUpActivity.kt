package com.example.learningleo.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.learningleo.domain.Profile
import com.example.learningleo.R
import com.example.learningleo.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.btnSignUp
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private val viewModel : FirebaseViewModel by lazy{
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp.setOnClickListener{signUp()}
        btnLogin.setOnClickListener{returnToLogin()}
    }

    private fun signUp() {

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val name = etName.text.toString()
        val phone = etPhone.text.toString()

        viewModel.signUp(email, password, name, phone, confirmPassword) { result: Int, text: String?, resultIntent: Intent? ->
            if(text != null)
            {
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
            if (result == Activity.RESULT_OK)
            {
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun returnToLogin()
    {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
