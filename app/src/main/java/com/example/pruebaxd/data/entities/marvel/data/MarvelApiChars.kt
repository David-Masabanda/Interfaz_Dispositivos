package com.example.pruebaxd.data.entities.marvel.data

import com.example.pruebaxd.data.entities.marvel.data.Data

data class MarvelApiChars(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)