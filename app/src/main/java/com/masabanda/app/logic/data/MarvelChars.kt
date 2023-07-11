package com.masabanda.app.logic.data

import android.os.Parcelable
import com.masabanda.app.data.entities.marvel.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(  val id: Int,
val name: String,
val comic: String,
val image: String


): Parcelable

fun MarvelChars.getMarvelCharsDB() : MarvelCharsDB{
    return MarvelCharsDB(
        id,name,comic,image
    )
}