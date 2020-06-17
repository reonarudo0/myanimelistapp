package com.example.learningleo.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseRetrofit(context: Context, baseUrl: String) {
    val retrofit: Retrofit
    val gson: Gson

    init {
        gson = GsonBuilder().create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}