package com.example.learningleo.interactor

import android.content.Context
import com.example.learningleo.repository.Marker
import com.example.learningleo.repository.RoomRepository

class RoomIteractor(context: Context)
{
    private val repository = RoomRepository(context)

    fun insert(marker: Marker){
        repository.insert(marker)
    }
    fun getByUser(user:String,callback: (markerArray: Array<Marker>?)->Unit){
        repository.getByUser(user,callback)
    }
}