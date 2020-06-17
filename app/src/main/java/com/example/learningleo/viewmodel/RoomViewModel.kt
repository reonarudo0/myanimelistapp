package com.example.learningleo.viewmodel

import android.app.Application
import android.app.SharedElementCallback
import androidx.lifecycle.AndroidViewModel
import com.example.learningleo.interactor.RoomIteractor
import com.example.learningleo.repository.Marker

class RoomViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = RoomIteractor(app.applicationContext)

    fun insert(marker: Marker){
        interactor.insert(marker)
    }
    fun getByUser(user:String,callback: (markerArray: Array<Marker>?)->Unit){
        interactor.getByUser(user,callback)
    }

}