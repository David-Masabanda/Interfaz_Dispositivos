package com.example.pruebas.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class MyLocationManager(val context : Context) {



    private lateinit var client : SettingsClient

    private fun initVars(){
        if (context!=null){
            client = LocationServices.getSettingsClient(context!!)

        }
    }

    fun getUserLocation(){
        initVars()
    }
}