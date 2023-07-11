package com.masabanda.app.ui.utilities

import android.app.Application
import androidx.room.Room
import com.masabanda.app.data.connections.MarvelConnectionDB


class ProyectoDispositivos : Application() {

    override fun onCreate() {
        super.onCreate()
        db=Room.databaseBuilder(applicationContext, MarvelConnectionDB::class.java,"marvelDB").build()

    }

    //Es un objeto que se crea dentro de una clase
    companion object{
        private var db: MarvelConnectionDB?=null

        fun getDBInstance():MarvelConnectionDB{
            return db!!
        }

    }
}