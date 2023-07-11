package com.masabanda.app.logic.marvelLogic

import android.util.Log
import com.masabanda.app.data.connections.ApiConnection
import com.masabanda.app.data.connections.MarvelConnectionDB
import com.masabanda.app.data.endpoints.MarvelEndpoint
import com.masabanda.app.data.entities.marvel.database.MarvelCharsDB
import com.masabanda.app.data.entities.marvel.database.getMarvelChars
import com.masabanda.app.data.entities.marvel.getMarvelChars
import com.masabanda.app.logic.data.MarvelChars
import com.masabanda.app.logic.data.getMarvelCharsDB
import com.masabanda.app.ui.utilities.ProyectoDispositivos

class MarvelLogic {

    suspend fun getAllCharacters(name:String,limit:Int):List<MarvelChars>{
        val itemList= arrayListOf<MarvelChars>()
        var call=ApiConnection.getService(ApiConnection.TypeApi.Marvel,MarvelEndpoint::class.java)
        if(call!=null){
            var response=call.getCharactersStartsWith(name,limit)
            if(response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m=it.getMarvelChars()
                    itemList.add(m)
                }
            }
            else{
                Log.d("UCE",response.toString())
            }
        }
        return itemList
    }

    suspend fun getAllMarvelChars(offset:Int,limit:Int):List<MarvelChars>{
        val itemList= arrayListOf<MarvelChars>()
        var call=ApiConnection.getService(ApiConnection.TypeApi.Marvel,MarvelEndpoint::class.java)
        if(call!=null){
            var response=call.getAllMarvelChars(offset ,limit)
            if(response.isSuccessful){
                response.body()!!.data.results.forEach{
                    val m=it.getMarvelChars()
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


    suspend fun getAllMarvelCharsDB():List<MarvelChars>{
        val item:ArrayList<MarvelChars> = arrayListOf()
        val itemsAux=ProyectoDispositivos.getDBInstance().marvelDao().getAllCharacters()

        itemsAux.forEach{
            item.add(
                it.getMarvelChars()
            )
        }
        return item
    }

    suspend fun insertMarvelCharsDB (items: List<MarvelChars>){
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach{itemsDB.add(it.getMarvelCharsDB())}
        ProyectoDispositivos.getDBInstance()
    }




}