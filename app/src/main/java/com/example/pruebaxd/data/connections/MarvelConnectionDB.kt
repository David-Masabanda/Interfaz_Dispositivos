package com.example.pruebaxd.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebaxd.data.dao.marvel.MarvelCharsDao
import com.example.pruebaxd.data.entities.marvel.data.database.MarvelCharsDB

@Database(entities = [MarvelCharsDB::class], version = 1)
abstract class MarvelConnectionDB:RoomDatabase() {
    abstract fun marvelDao(): MarvelCharsDao
}