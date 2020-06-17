package com.example.learningleo.interactor

import android.content.Context
import com.example.learningleo.domain.Anime
import com.example.learningleo.repository.JikanRepository

class JikanIteractor(context: Context){
    val BASE_URL = "https://jikan1.p.rapidapi.com/"
    private val jikanRepository = JikanRepository(context,BASE_URL)

    fun upcoming(current: String,callback: (animes: Array<Anime>) -> Unit)
    {
        jikanRepository.upcoming(current,callback)
    }

    fun currentSeason(callback: (animes: Array<Anime>) -> Unit)
    {
        jikanRepository.currentSeason(callback)
    }

    fun animeByGenre(genreId: Int,callback: (animes: Array<Anime>,title:String) -> Unit)
    {
        jikanRepository.animeByGenre(genreId,callback)
    }
}