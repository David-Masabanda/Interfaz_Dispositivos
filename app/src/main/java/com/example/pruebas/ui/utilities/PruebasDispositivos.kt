package com.example.pruebas.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.pruebas.data.connections.MarvelConnectionDB

class PruebasDispositivos : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
        ).build()
    }

    companion object{
        private var db: MarvelConnectionDB?=null

        fun getDBinstance(): MarvelConnectionDB{
            return db!!
        }
    }
}