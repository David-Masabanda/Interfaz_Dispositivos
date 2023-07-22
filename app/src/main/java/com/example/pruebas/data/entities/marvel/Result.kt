package com.example.pruebas.data.entities.marvel

import com.example.pruebas.logic.data.MarvelChars

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)


fun Result.getMarvelChars(): MarvelChars {
    var comic: String = ""
    if (comics.items.size > 0) {
        comic = comics.items[0].name
    }

    val m = MarvelChars(
        id,
        name,
        comic,
        thumbnail.path + "." + thumbnail.extension,
        description
    )
    return m
}