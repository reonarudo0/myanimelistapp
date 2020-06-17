package com.example.learningleo.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.learningleo.R
import com.example.learningleo.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_resend_password.*

class ResendPasswordActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by lazy{
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resend_password)

        btnResendPassword.setOnClickListener{resendPassword()}
    }

    private fun resendPassword()
    {
        val email = etEmail.text.toString()

        viewModel.resendPassword(email){result: Int, text: String?, resultIntent: Intent? ->
            if(text != null)
            {
                Toast.makeText(this,text,Toast.LENGTH_LONG).show()
            }
            if(result == Activity.RESULT_OK)
            {
                setResult(Activity.RESULT_OK,resultIntent)
                finish()
            }
        }
    }
}
