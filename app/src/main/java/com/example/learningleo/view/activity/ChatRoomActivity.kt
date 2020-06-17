package com.example.learningleo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.learningleo.R
import com.example.learningleo.viewmodel.ChatBotViewModel
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private val viewModel: ChatBotViewModel by lazy {
        ViewModelProvider(this). get(ChatBotViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        etAnswer.setText("Oi")
        sendAnswer()

        btnSend.setOnClickListener{ sendAnswer()}
    }

    private fun sendAnswer()
    {
        val answer = etAnswer.text.toString()
        viewModel.send(answer){reply:String? ->
            Log.d("INFO",reply)
            tvReply.setText(reply)
            tvReply.movementMethod = ScrollingMovementMethod()
        }
        etAnswer.getText().clear()
    }



}
