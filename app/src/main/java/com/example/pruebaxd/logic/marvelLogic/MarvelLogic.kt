package com.example.pruebaxd.logic.marvelLogic

import android.util.Log
import com.example.pruebaxd.data.connections.ApiConnection
import com.example.pruebaxd.data.endpoints.JikanEndPoint
import com.example.pruebaxd.data.endpoints.MarvelEndPoint
import com.example.pruebaxd.data.marvel.MarvelPersonajes

class MarvelLogic {
    suspend fun getAllAnimes(name:String,limit:Int):List<MarvelPersonajes>{

        var itemList= arrayListOf<MarvelPersonajes>()

        var call=
            ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndPoint::class.java)
        if (call!=null){
            var response=call.getCharactersStartsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    var commic:String=""
                    if(it.comics.items.size>0){
                        commic=it.comics.items[0].name
                    }
                    val m= MarvelPersonajes(
                        it.id,
                        it.name,
                        commic,
                        it.thumbnail.path+"."+it.thumbnail.extension
                    )
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }


        //Compruebo si la respuesta se ejecuto

        return itemList
    }
}