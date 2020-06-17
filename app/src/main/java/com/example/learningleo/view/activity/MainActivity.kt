package com.example.learningleo.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningleo.R
import com.example.learningleo.view.adapter.JikanAdapter
import com.example.learningleo.viewmodel.FirebaseViewModel
import com.example.learningleo.viewmodel.JikanViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class MainActivity : AppCompatActivity() {
    private val jikanViewModel : JikanViewModel by lazy{
        ViewModelProvider(this).get(JikanViewModel::class.java)
    }
    private val firebaseViewModel : FirebaseViewModel by lazy{
        ViewModelProvider(this).get(FirebaseViewModel::class.java)
    }
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userEmail = intent.getStringExtra("Email")
        val name = intent.getStringExtra("Name")
        val greetings = "Wellcome $name"
        tvGreetings.setText(greetings)

        btnMaps.setOnClickListener{mapsActivity()}
        btnChat.setOnClickListener{chatActivity()}
        btnLogout.setOnClickListener{logout()}
        configureRecyclerView()
        showAnimes()
    }

    private fun configureRecyclerView()
    {
        rvAnimes.layoutManager = LinearLayoutManager(this)
    }

    fun showAnimes()
    {
        jikanViewModel.result.observe(this, Observer { animes->
            val adapter = JikanAdapter(this,animes){anime ->
                val intent = Intent(this,AnimeActivity::class.java)
                intent.putExtra("Title",anime.title)
                intent.putExtra("Genre",anime.genres?.joinToString (separator = ", ",prefix = "",postfix = ""))
                intent.putExtra("Score",anime.score)
                intent.putExtra("Episodes",anime.episodes)
                intent.putExtra("ImageUrl",anime.imageUrl)
                intent.putExtra("Synopsis",anime.synopsis)
                startActivity(intent)
            }
            rvAnimes.adapter = adapter
        })

        CoroutineScope(IO).launch { getAnimes() }
    }

    suspend fun getAnimes()= coroutineScope{
        jikanViewModel.currentSeason()
        //jikanViewModel.upcoming(0,"airing","Top Airing")
        delay(700)
        jikanViewModel.upcoming(1,"upcoming","Upcoming")
        for (i in 1..2)
        {
            jikanViewModel.animeByGenre(i)
            delay(700)
        }
        jikanViewModel.animeByGenre(4)
        delay(700)
        jikanViewModel.animeByGenre(7)
        delay(700)
        jikanViewModel.animeByGenre(30)
    }

    private fun logout()
    {
        firebaseViewModel.logout{
            finish()
        }
    }

    private fun chatActivity()
    {
        val intentMain = Intent(this, ChatRoomActivity::class.java)
        startActivity(intentMain)
    }

    private fun mapsActivity()
    {
        val intentMain = Intent(this, MapsActivity::class.java)
        intentMain.putExtra("Email",userEmail)
        startActivity(intentMain)
    }
}
