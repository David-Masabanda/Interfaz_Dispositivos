package com.example.dm_proyecto2_pedidosonline.Logic.lists


import com.example.pruebaxd.data.entities.LoginUser
import com.example.pruebaxd.data.entities.marvel.MarvelChars

class listItems {

    fun returnItems():List<LoginUser>{
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
                "Spiderman",
                "Spiderman (1962)",
//                "Peter Parker was bitten by a radioactive spider as a teenager, granting him spider-like powers. After the death of his Uncle Ben, Peter learned that with great power, comes great responsibility. Swearing to always protect the innocent from harm, Peter Parker became Spider-Man.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ),
            MarvelChars(
                2,
                "Invisible Woman",
                "Fantastic Four 1 (1961)",
//                "Susan Storm is a founding member of the Fantastic Four and later the Future Foundation. She is able to create invisible force fields of any shape she conceives and able to turn herself and anything she's in contact with invisible. Sue is the wife of Reed Richards and the mother of their children, Franklin and Valeria.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11141/111413247/7267710-4df69cb3-7b54-480e-89e5-2c7e68c52b1b_rw_1200.jpg"
            ),
            MarvelChars(
                3,
                "Eternity",
                "Strange Tales (1965)",
//                "Eternity is an abstract, relatively omnipotent entity representing all time and reality in the universe. Eternity has been around before the beginning of the universe. He is eternal.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/10/105264/6154236-eternity%20universe.jpg"
            ),
            MarvelChars(
                4,
                "Black Widow",
                "The Avengers (1963)",
//                "A former KGB agent, Natasha Alianovna Romanova, better known as Natasha Romanoff or Black Widow, was trained by the top-secret Soviet brainwashing and training program, the Red Room, to become the ultimate \"Super-Spy\". She defected from the Soviet Union to the US to join S.H.I.E.L.D. Subsequently, Black Widow proceeded to become a member of the Avengers and has been on the team on numerous occasions. She's a woman with an enigmatic past which very few are acquainted with.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/14487/7835285-b7f54158-13c3-4646-bbee-033a765c570d.jpeg"
            ),
            MarvelChars(
                5,
                "Scarlet Witch",
                "The Avengers (1963)",
//                "The world knows Wanda Maximoff as the Scarlet Witch, with probability manipulation and reality-warping abilities. The twin sister of Quicksilver, mother to Wiccan and Speed, and ex-wife of the Vision; Wanda has taken on many roles throughout her life but will forever be known for causing the Decimation.",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926321-large-3540780.jpg"
            )

        )
        return items
    }
}