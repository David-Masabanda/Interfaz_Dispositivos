package com.example.pruebaxd.data.entities.marvel.data

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)