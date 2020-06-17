package com.example.learningleo.repository

import android.content.Context
import com.example.learningleo.domain.Anime
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

interface JikanService{
    @GET("top/anime/1/{current}")
    fun upcoming(
        @Path("current") current: String,
        @Header("x-rapidapi-host") xRapidapiHost: String = "jikan1.p.rapidapi.com",
        @Header("x-rapidapi-key") xRapidapiKey: String = "5f8aceb7f3msh7b3605d4b2b1346p18712bjsn22fb7c8f159a"
    ): Call<UpcomingAnimeListDTO>

    @GET("season/2020/spring")
    fun currentSeason(
        @Header("x-rapidapi-host") xRapidapiHost: String = "jikan1.p.rapidapi.com",
        @Header("x-rapidapi-key") xRapidapiKey: String = "5f8aceb7f3msh7b3605d4b2b1346p18712bjsn22fb7c8f159a"
    ): Call<AnimeListDTO>

    @GET("genre/anime/{genre_id}/1")
    fun animeByGenre(
        @Path("genre_id") genreId: Int,
        @Header("x-rapidapi-host") xRapidapiHost: String = "jikan1.p.rapidapi.com",
        @Header("x-rapidapi-key") xRapidapiKey: String = "5f8aceb7f3msh7b3605d4b2b1346p18712bjsn22fb7c8f159a"
    ): Call<GenreAnimeListDTO>
}

class JikanRepository(context: Context, baseUrl: String) : BaseRetrofit(context,baseUrl){
    private val service =retrofit.create(JikanService::class.java)

    fun upcoming(current: String,callback: (animes: Array<Anime>) -> Unit){
        service.upcoming(current).enqueue(object : Callback<UpcomingAnimeListDTO>{

            override fun onResponse(call: Call<UpcomingAnimeListDTO>, response: Response<UpcomingAnimeListDTO>) {
                val result = mutableListOf<Anime>()
                val animes = response.body()?.results

                animes?.forEach { a ->
                    val genres = mutableListOf<String>()
                    a.genres?.forEach { g->
                        g.name?.let { genres.add(it) }
                    }
                    val anime = Anime(
                        id = a.id,
                        title = a.title,
                        synopsis = a.synopsis,
                        imageUrl = a.imageUrl,
                        score = a.score,
                        episodes = a.episodes,
                        genres = genres
                    )
                    result.add(anime)
                }

                callback(result.toTypedArray())
            }

            override fun onFailure(call: Call<UpcomingAnimeListDTO>, t: Throwable) {
                callback(arrayOf())
            }
        })
    }

    fun currentSeason(callback: (animes: Array<Anime>) -> Unit){
        service.currentSeason().enqueue(object: Callback<AnimeListDTO>{
            override fun onResponse(call: Call<AnimeListDTO>, response: Response<AnimeListDTO>) {
                val result = mutableListOf<Anime>()
                val animes = response.body()?.results

                animes?.forEach { a ->
                    val genres = mutableListOf<String>()
                    a.genres?.forEach { g->
                        g.name?.let { genres.add(it) }
                    }
                    val anime = Anime(
                        id = a.id,
                        title = a.title,
                        synopsis = a.synopsis,
                        imageUrl = a.imageUrl,
                        score = a.score,
                        episodes = a.episodes,
                        genres = genres
                    )
                    result.add(anime)
                }

                callback(result.toTypedArray())
            }

            override fun onFailure(call: Call<AnimeListDTO>, t: Throwable) {
                callback(arrayOf())
            }
        })
    }

    fun animeByGenre(genreId: Int,callback: (animes: Array<Anime>,title:String) -> Unit)
    {
        service.animeByGenre(genreId).enqueue(object: Callback<GenreAnimeListDTO>{
            override fun onResponse(call: Call<GenreAnimeListDTO>, response: Response<GenreAnimeListDTO>) {
                val result = mutableListOf<Anime>()
                val animes = response.body()?.results
                val title = response.body()?.genre?.name.toString()

                animes?.forEach { a ->
                    val genres = mutableListOf<String>()
                    a.genres?.forEach { g->
                        g.name?.let { genres.add(it) }
                    }
                    val anime = Anime(
                        id = a.id,
                        title = a.title,
                        synopsis = a.synopsis,
                        imageUrl = a.imageUrl,
                        score = a.score,
                        episodes = a.episodes,
                        genres = genres
                    )
                    result.add(anime)
                }
                callback(result.toTypedArray(),title)
            }

            override fun onFailure(call: Call<GenreAnimeListDTO>, t: Throwable) {
                callback(arrayOf(),"")
            }
        })
    }


}
