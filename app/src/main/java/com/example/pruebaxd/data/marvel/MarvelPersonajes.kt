package com.example.pruebaxd.data.marvel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelPersonajes (

    val id: Int,
    val nombre: String,
    val comic: String,
    val imagen: String,
    val resumen: String,
): Parcelable