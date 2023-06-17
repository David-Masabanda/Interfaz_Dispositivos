package com.example.pruebaxd.logic.validator

import com.example.pruebaxd.data.marvel.MarvelPersonajes
import com.example.pruebaxd.logic.entities.LoginUser

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
    fun returnMarvelChars():List<MarvelPersonajes>{
        val items= listOf(
            MarvelPersonajes(
                1,
                "Wolverine",
                "The Uncanny X-Men",
                "https://comicvine.gamespot.com/a/uploads/original/4/46529/8089182-783_1000%283%29.jpg"
            ),
            MarvelPersonajes(
                2,
                "Spiderman",
                "The Amazing Spider Man",
                "https://comicvine.gamespot.com/a/uploads/scale_large/6/67663/4851257-01.jpg"
            ),
            MarvelPersonajes(
                3,
                "Hulk",
                "The Inmortal Hulk",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg"
            ),
            MarvelPersonajes(
                4,
                "Deadpool",
                "Wolverine and Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),
            MarvelPersonajes(
                5,
                "Iron Man",
                "Superior Iron Man",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8654427-ezgif-1-2f113089e4.jpg"
            )
        )


        return items
    }
}