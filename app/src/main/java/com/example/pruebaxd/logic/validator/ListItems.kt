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
                "https://comicvine.gamespot.com/a/uploads/original/4/46529/8089182-783_1000%283%29.jpg",
                "Han pasado los años y los mutantes están en declive. Logan, débil y cansado, vive alejado de todos hasta que acepta una última misión de Charles Xavier: la de proteger a una joven especial, de nombre Laura Kinney pero conocida como X-23, la última esperanza de la raza mutante."
            ),
            MarvelPersonajes(
                2,
                "Spiderman",
                "The Amazing Spider Man",
                "https://comicvine.gamespot.com/a/uploads/scale_large/6/67663/4851257-01.jpg",
                "Tras descubrirse la identidad secreta de Peter Parker como Spider-Man, la vida del joven se vuelve una locura. Peter le pide ayuda al Doctor Strange para recuperar su vida, pero algo sale mal y provoca una fractura en el multiverso."
            ),
            MarvelPersonajes(
                3,
                "Hulk",
                "The Inmortal Hulk",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg",
                "Bruce Banner recorre el mundo en busca de un antídoto para librarse de su alter ego. Además tendrá que hacer frente a Emil, un nuevo enemigo, lo que convertirá a Nueva York en el escenario de la última batalla entre las dos criaturas más poderosas."
            ),
            MarvelPersonajes(
                4,
                "Deadpool",
                "Wolverine and Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg",
                "Un exmercenario quien, tras haber sido sometido a un cruel experimento, adquiere el superpoder de sanar rápidamente y pretende vengarse del hombre que destrozó su vida."
            ),
            MarvelPersonajes(
                5,
                "Iron Man",
                "Superior Iron Man",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8654427-ezgif-1-2f113089e4.jpg",
                "El descarado y brillante Tony Stark, tras ver destruido todo su universo personal, debe encontrar y enfrentarse a un enemigo cuyo poder no conoce límites. Este viaje pondrá a prueba su entereza una y otra vez, y le obligará a confiar en su ingenio."
            )
        )


        return items
    }
}