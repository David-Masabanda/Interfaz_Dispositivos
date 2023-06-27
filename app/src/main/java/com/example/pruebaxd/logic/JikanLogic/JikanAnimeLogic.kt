package com.example.pruebaxd.logic.JikanLogic

import com.example.pruebaxd.data.connections.ApiConnection
import com.example.pruebaxd.data.endpoints.JikanEndPoint
import com.example.pruebaxd.data.marvel.MarvelPersonajes

class JikanAnimeLogic {

    suspend fun getAllAnimes():List<MarvelPersonajes>{

        var itemList= arrayListOf<MarvelPersonajes>()

        var response=ApiConnection.getService(ApiConnection.TypeApi.Jikan,JikanEndPoint::class.java).getAllAnimes()


        //Compruebo si la respuesta se ejecuto
        if (response.isSuccessful){
            response.body()!!.data.forEach{
                val m=MarvelPersonajes(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}