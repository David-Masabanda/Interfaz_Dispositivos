package com.example.pruebaxd.data.entities.marvel

import android.os.Parcelable
import com.example.pruebaxd.data.entities.marvel.data.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val nombre: String,
    val comic: String,
//    val descripcion: String,
    val imagen: String
) : Parcelable
fun MarvelChars.getMarvelCharsDB(): MarvelCharsDB {
    return MarvelCharsDB(
        id,
        nombre,
        comic,
        imagen
    )
}
