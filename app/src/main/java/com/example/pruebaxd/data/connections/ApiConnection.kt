package com.example.pruebaxd.data.connections


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConnection {


    //Se conecta a una url base
    //debemos usar el comvertidor l14
    //Ponemos dentro de una funcion

  /*  fun getJikanConnection(): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
        Funciona pero no es lo masoptimo crear para cada uno
        */
    enum class TypeApi{
        Jikan,Marvel
    }
    private val API_JIKAN="https://api.jikan.moe/v4/"
    private val API_MARVEL="https://gateway.marvel.com/v1/public/"
  fun getConnection(base:String): Retrofit{
      var retrofit = Retrofit.Builder()
          .baseUrl(base)
          .addConverterFactory(GsonConverterFactory.create())
          .build()

      return retrofit
    }
    suspend fun <T,E:Enum<E>>getService(api:E,service:Class<T>):T   {
        var BASE=""

        when(api.name){
            TypeApi.Jikan.name->{
                BASE= API_JIKAN

            }
            TypeApi.Marvel.name->{
                BASE= API_MARVEL
            }
        }
        return getConnection(BASE).create(service)


    }


}