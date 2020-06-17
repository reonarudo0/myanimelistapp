package com.example.learningleo.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.learningleo.R
import kotlinx.android.synthetic.main.activity_login.*
import com.example.learningleo.viewmodel.FirebaseViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy {
        ViewModelProvider(this). get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{ login()}
        btnSignUp.setOnClickListener{ signUp()}
        btnForgotPassword.setOnClickListener{forgotPassword()}
    }

    private fun login()
    {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        viewModel.login(email,password) { result, text,email ->
            if(result == Activity.RESULT_OK)
            {
                val intentMain = Intent(this, MainActivity::class.java)
                intentMain.putExtra("Name",text)
                intentMain.putExtra("Email",email)
                startActivity(intentMain)
            }
            else
            {
                Toast.makeText(this,text,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signUp()
    {
        val intentMain = Intent(this, SignUpActivity::class.java)
        startActivityForResult(intentMain,1)
    }

    private fun forgotPassword()
    {
        val intentMain = Intent(this,
            ResendPasswordActivity::class.java)
        startActivityForResult(intentMain,1)
    }

    override fun onActivityResult(request: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(request, resultCode, data)
        if(request ==  1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                val email = data?.getStringExtra("Email")
                val password = data?.getStringExtra("Password")
                etEmail.setText(email)
                etPassword.setText(password)
            }
        }
    }


}