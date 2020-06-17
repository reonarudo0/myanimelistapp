package com.example.learningleo.repository

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    val name: String? = null
)

data class AnimeDTO(
    @SerializedName("mal_id")
    val id: Int? = null,

    val title: String? = null,

    @SerializedName("image_url")
    val imageUrl:String? = null,

    val synopsis:String? = null,

    val episodes: String? = null,

    val score: String? = null,

    val genres: Array<GenreDTO>? = null
)

data class AnimeListDTO(
    @SerializedName("anime")
    val results: Array<AnimeDTO>? = null
)

data class GenreAnimeListDTO(
    @SerializedName("anime")
    val results: Array<AnimeDTO>? = null,

    @SerializedName("mal_url")
    val genre: GenreDTO? = null
)

data class UpcomingAnimeListDTO(val str: String,
    @SerializedName("top")
    val results: Array<AnimeDTO>? = null
)