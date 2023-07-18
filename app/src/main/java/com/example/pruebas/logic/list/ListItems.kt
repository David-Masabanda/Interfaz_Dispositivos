package com.example.pruebas.logic.list

import com.example.pruebas.data.LoginUser
import com.example.pruebas.data.entities.marvel.MarvelChars

class ListItems {

    fun returnItems(): List<LoginUser>{
        var items= listOf<LoginUser>(
            LoginUser("1","1"),
            LoginUser("2","1"),
            LoginUser("3","1"),
            LoginUser("4","1"),
            LoginUser("5","1")
        )
        return items
    }
    fun returnMarvelChars():List<MarvelChars>{
        val items= listOf(
            MarvelChars(
                1,
                "Wolverine",
                "The Uncanny X-Men",
                "https://comicvine.gamespot.com/a/uploads/original/4/46529/8089182-783_1000%283%29.jpg",
                "Wolverine, cuyo nombre de nacimiento es James Howlett, \u200B es un superhéroe y antihéroe ficticio que aparece en los cómics publicados por Marvel Comics, principalmente en asociación con los X-Men. "
            ),
            MarvelChars(
                2,
                "Spiderman",
                "The Amazing Spider Man",
                "https://comicvine.gamespot.com/a/uploads/scale_large/6/67663/4851257-01.jpg",
                "Luego de sufrir la picadura de una araña genéticamente modificada, un estudiante de secundaria tímido y torpe adquiere increíbles capacidades como arácnido. Pronto comprenderá que su misión es utilizarlas para luchar contra el mal y defender a sus vecinos."
            ),
            MarvelChars(
                3,
                "Hulk",
                "The Inmortal Hulk",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg",
                "Bruce Banner recorre el mundo en busca de un antídoto para librarse de su alter ego. Además tendrá que hacer frente a Emil, un nuevo enemigo, lo que convertirá a Nueva York en el escenario de la última batalla entre las dos criaturas más poderosas."
            ),
            MarvelChars(
                4,
                "Deadpool",
                "Wolverine and Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg",
                "Un exmercenario quien, tras haber sido sometido a un cruel experimento, adquiere el superpoder de sanar rápidamente y pretende vengarse del hombre que destrozó su vida."
            ),
            MarvelChars(
                5,
                "Iron Man",
                "Superior Iron Man",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8654427-ezgif-1-2f113089e4.jpg",
                "Un empresario millonario construye un traje blindado y lo usa para combatir el crimen y el terrorismo."
            )
        )


        return items
    }
}