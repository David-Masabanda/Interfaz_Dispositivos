package com.masabanda.app.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.masabanda.app.data.entities.marvel.database.MarvelCharsDB

@Dao
interface MarvelCharsDao {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters():List<MarvelCharsDB>
    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacter(id:Int):List<MarvelCharsDB>

    @Insert
    fun insertCharacter(ch:List<MarvelCharsDB>):List<MarvelCharsDB>
    @Query("select * from MarvelCharsDB where id=:id")
    fun deleteCharacter(id:Int):List<MarvelCharsDB>
    @Query("select * from MarvelCharsDB where id=:id")
    fun updateCharacter(id:Int):List<MarvelCharsDB>
}