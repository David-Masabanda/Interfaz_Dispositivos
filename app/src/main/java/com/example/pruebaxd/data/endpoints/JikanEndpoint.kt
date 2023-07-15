package com.example.pruebaxd.data.endpoints

import com.example.pruebaxd.data.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>

    //Aqui van todos los llamados que se necesitan
}