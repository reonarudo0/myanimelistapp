package com.example.learningleo.domain

data class Anime(
    val id: Int? = null,
    val title: String? = null,
    val imageUrl:String? = null,
    val synopsis:String? = null,
    val episodes: String? = null,
    val score: String? = null,
    val genres: MutableList<String>? = null
)
