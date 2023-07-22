package com.example.pruebas.logic.marvelLogic

import android.util.Log
import com.example.pruebas.data.connections.ApiConnection
import com.example.pruebas.data.endpoints.MarvelEndPoint
import com.example.pruebas.data.entities.marvel.database.MarvelCharsDB
import com.example.pruebas.data.entities.marvel.database.getMarvelChars
import com.example.pruebas.data.entities.marvel.getMarvelChars
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.logic.data.getMarvelCharsDB
import com.example.pruebas.ui.utilities.PruebasDispositivos
import java.lang.RuntimeException

class MarvelLogic {

    suspend fun getMarvelChars(name:String,limit:Int): ArrayList<MarvelChars>{

        var itemList= arrayListOf<MarvelChars>()

        var call= ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndPoint::class.java)

        if (call!=null){
            var response=call.getCharactersStarsWith(name,limit)

            if (response.isSuccessful){
                response.body()!!.data.results.forEach{
//                    var comic:String=""
//                    if(it.comics.items.size>0){
//                        comic=it.comics.items[0].name
//                    }
//                    val m= MarvelChars(
//                        it.id,
//                        it.name,
//                        comic,
//                        it.thumbnail.path+"."+it.thumbnail.extension,
//                        it.description
//                    )
                    val m = it.getMarvelChars()
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



    suspend fun getAllMarvelChars(offset : Int, limit : Int): ArrayList<MarvelChars>{
        var itemList= arrayListOf<MarvelChars>()
        var call = ApiConnection.getService(ApiConnection.TypeApi.Marvel, MarvelEndPoint::class.java)

        if(call!=null){
            val response = call.getAllMarvelCharacters(offset,limit)
            if (response.isSuccessful){
                response.body()!!.data.results.forEach {
                    val m = it.getMarvelChars()
                    itemList.add(m)
                }
            }else{
                Log.d("UCE", response.toString())
            }
        }
        return itemList
    }


    suspend fun getAllMarvelCharsDB(): List<MarvelChars>{
        var items: ArrayList<MarvelChars> = arrayListOf()
        PruebasDispositivos.getDBinstance().marvelDao().getAllCharacters()
            .forEach {
                items.add(it.getMarvelChars())
        }
        return items
    }

    suspend fun insertMarvelCharsDB(items: List<MarvelChars>){
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }
        PruebasDispositivos.getDBinstance().marvelDao().insertMarvelChar(itemsDB)
    }

    suspend fun getInitChars(limit: Int, offset: Int): MutableList<MarvelChars>{
        var items = mutableListOf<MarvelChars>()
        try{
            items= MarvelLogic().getAllMarvelCharsDB().toMutableList()

            if (items.isEmpty()){
                items=(MarvelLogic().getAllMarvelChars(offset,limit))
                MarvelLogic().insertMarvelCharsDB(items)
            }
            items
        }catch (ex: Exception){
            throw RuntimeException(ex.message)
        }

        return items

    }



}