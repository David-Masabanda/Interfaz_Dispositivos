package com.masabanda.app.data.endpoints

import com.masabanda.app.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {

    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>


}