package com.example.pruebaxd.data.endpoints

import com.example.pruebaxd.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response

import retrofit2.http.GET


internal interface JikanEndPoint {
    //Debemos
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>
}
