package com.example.pruebas.logic.marvelLogic

import android.util.Log
import com.example.pruebas.data.connections.ApiConnection
import com.example.pruebas.data.endpoints.MarvelEndPoint
import com.example.pruebas.data.entities.marvel.MarvelChars

class MarvelLogic {

    suspend fun getAllChars(name:String,limit:Int):List<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call= ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndPoint::class.java)

        if (call!=null){
            var response=call.getCharactersStarsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
                    var comic:String=""
                    if(it.comics.items.size>0){
                        comic=it.comics.items[0].name
                    }
                    val m= MarvelChars(
                        it.id,
                        it.name,
                        comic,
                        it.thumbnail.path+"."+it.thumbnail.extension,
                        it.description
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