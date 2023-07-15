package com.example.pruebaxd.logic.jikanLogic

import com.example.pruebaxd.data.connections.ApiConnection
import com.example.pruebaxd.data.endpoints.JikanEndpoint
import com.example.pruebaxd.data.entities.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes():List<MarvelChars>{

        var itemList = arrayListOf<MarvelChars>()

        var response= ApiConnection.getService(
            ApiConnection.TypeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()

        if (response.isSuccessful){
            response.body()!!.data.forEach {
                val m= MarvelChars(
                    it.mal_id,
                    it.title,
//                    it.titles[0].title,
                    it.duration,
//                    it.synopsis,
                    it.images.jpg.image_url,
//                    it.rating
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}
