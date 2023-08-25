package com.example.pruebas.logic.data

import android.os.Parcelable
import com.example.pruebas.data.entities.marvel.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize

data class MarvelChars(
    val id: Int,
    val nombre: String,
    val comic: String,
    val imagen: String,
    val desc: String
):Parcelable

fun MarvelChars.getMarvelCharsDB(): MarvelCharsDB {
    return MarvelCharsDB(
        id,
        nombre,
        comic,
        imagen,
        desc
    )
}