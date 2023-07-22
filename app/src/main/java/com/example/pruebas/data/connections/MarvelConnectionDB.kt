package com.example.pruebas.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebas.data.dao.marvel.MarvelCharsDAO
import com.example.pruebas.data.entities.marvel.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {
    abstract fun marvelDao() : MarvelCharsDAO
}