package com.example.learningleo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.example.learningleo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_anime.*
import kotlinx.android.synthetic.main.jikan_vertical_rv.tvTitle

class AnimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)

        val title = intent.getStringExtra("Title")
        val genres = "Genres: "+intent.getStringExtra("Genre")
        val synopsis = intent.getStringExtra("Synopsis")
        val episodes = "Episodes: "+intent.getStringExtra("Episodes")
        val score = "Score: "+intent.getStringExtra("Score")
        val imageUrl = intent.getStringExtra("ImageUrl")


        tvTitle.setText(title)
        tvGenre.setText(genres)
        tvSynopsis.setText(synopsis)
        tvEpisodes.setText(episodes)
        tvScore.setText(score)
        tvSynopsis.movementMethod = ScrollingMovementMethod()
        Picasso.get().load(imageUrl).into(imPoster)
    }


}
