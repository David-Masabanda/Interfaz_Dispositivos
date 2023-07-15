package com.example.pruebaxd.data.entities.marvel.data

import com.example.pruebaxd.data.entities.marvel.data.Item

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)