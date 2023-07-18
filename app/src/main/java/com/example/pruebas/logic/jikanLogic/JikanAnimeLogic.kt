package com.example.pruebas.logic.jikanLogic

import com.example.pruebas.data.connections.ApiConnection
import com.example.pruebas.data.endpoints.JikanEndPoint
import com.example.pruebas.data.entities.marvel.MarvelChars
import retrofit2.create

class JikanAnimeLogic {

    suspend fun getAllAnimes() :List<MarvelChars>{

        val response=ApiConnection.getService(
            ApiConnection.TypeApi.Jikan,
            JikanEndPoint::class.java).getAllAnimes()

        var itemList = arrayListOf<MarvelChars>()

        if(response.isSuccessful){
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url,
                    it.synopsis
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}