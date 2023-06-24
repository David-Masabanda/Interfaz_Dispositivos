package com.example.pruebaxd.data.connections


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConnection {

    //Se conecta a una url base
    //debemos usar el comvertidor l14
    //Ponemos dentro de una funcion

    fun getJikanConnection(): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

}