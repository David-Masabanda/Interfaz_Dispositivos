package com.example.pruebaxd.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.pruebaxd.data.connections.MarvelConnectionDB

class Pruebaxd : Application(){

    val name_class : String = "Admin"
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
                ).build()


    }

    companion object {

       private  var db : MarvelConnectionDB? = null
        fun getDbInstance() : MarvelConnectionDB{
            return db!!
        }
    }
}