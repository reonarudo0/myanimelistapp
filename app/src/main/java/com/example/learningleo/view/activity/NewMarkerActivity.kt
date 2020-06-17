package com.example.learningleo.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.learningleo.R
import kotlinx.android.synthetic.main.activity_new_marker.*

class NewMarkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_marker)



        btnConfirm.setOnClickListener{confirm()}
    }

    private fun confirm()
    {
        val loc = this.intent.getStringExtra("Loc").toString()
        val resultIntent = Intent()
        resultIntent.putExtra("Name",etName.text.toString())
        resultIntent.putExtra("Loc",loc)
        setResult(Activity.RESULT_OK,resultIntent)
        finish()
    }

}
