package com.example.pruebaxd.data.entities.marvel

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)