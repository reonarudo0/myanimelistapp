package com.example.learningleo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.learningleo.domain.AnimeGroup
import com.example.learningleo.interactor.JikanIteractor


class JikanViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = JikanIteractor(app.applicationContext)

    val result = MutableLiveData<Array<AnimeGroup>>()
    val orderMap = HashMap<Int, AnimeGroup>()
    var animeArr = mutableListOf<AnimeGroup>()

    fun upcoming(id:Int,current: String,title: String)
    {
        interactor.upcoming(current) { animes ->
            val upcoming = AnimeGroup(title,animes)
            orderMap.put(id,upcoming)
            updateData()
        }
    }

    fun currentSeason()
    {
        interactor.currentSeason { animes ->
            val title = "Current Season"
            val currentSeason = AnimeGroup(title,animes)
            orderMap.put(0,currentSeason)
            updateData()
        }
    }

    fun animeByGenre(genreId: Int)
    {
        interactor.animeByGenre(genreId){animes,title->
            val newTitle = title.split(" ")[0]
            val animeByGenre = AnimeGroup(newTitle,animes)
            orderMap.put(genreId+1,animeByGenre)
            updateData()
        }
    }

    private fun updateData()
    {
        animeArr.clear()
        orderMap.forEach{(key,value)->
            animeArr.add(value)
        }
        result.value = animeArr.toTypedArray()
    }
}


