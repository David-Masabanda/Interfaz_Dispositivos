package com.example.pruebaxd.data.entities.marvel.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebaxd.data.entities.marvel.MarvelChars
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(@PrimaryKey(autoGenerate = true)
                         val id:Int,
                         val name: String,
                         val comic:String,
                         val image:String
                         ):Parcelable
fun MarvelCharsDB.getMarvelChars(): MarvelChars {
    return MarvelChars(
        id,
        name,
        comic,
        image
    )
}