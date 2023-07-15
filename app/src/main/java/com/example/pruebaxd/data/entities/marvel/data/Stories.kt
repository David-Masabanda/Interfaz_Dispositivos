package com.example.pruebaxd.data.entities.marvel.data

import com.example.pruebaxd.data.entities.marvel.data.ItemXXX

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)