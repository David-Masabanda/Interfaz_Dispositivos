package com.example.pruebas.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class BiometricViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    suspend fun chargingData(){
        isLoading.postValue(true)
        delay(5000)
        isLoading.postValue(false)
    }
}