package com.example.pruebas.data.endpoints

import com.example.pruebas.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndPoint {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>
}