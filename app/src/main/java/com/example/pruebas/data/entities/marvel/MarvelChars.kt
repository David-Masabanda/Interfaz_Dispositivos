package com.example.pruebas.data.entities.marvel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val nombre: String,
    val comic: String,
    val imagen: String,
    val desc: String
):Parcelable
