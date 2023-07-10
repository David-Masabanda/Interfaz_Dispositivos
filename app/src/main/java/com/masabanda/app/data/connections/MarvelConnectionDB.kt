package com.masabanda.app.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masabanda.app.data.entities.marvel.database.MarvelCharsDB
import com.masabanda.app.data.dao.marvel.MarvelCharsDao


@Database(
    entities=[MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB: RoomDatabase()  {
    abstract fun marvelDao(): MarvelCharsDao
}