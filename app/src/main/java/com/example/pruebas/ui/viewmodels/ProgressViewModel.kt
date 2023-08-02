package com.example.pruebas.ui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {

    //Son como paquetes, va a englobar a la variable o estado
    val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()

    fun processBackground(value:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)
            delay(value)
            Log.d("UCE", "Termino la corrutina")
            val state1 = View.GONE
            progressState.postValue(state1)
        }
    }


    fun sumInBackground(value:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)

            var total = 0
            for (i in 1..300000){
                total+=1
            }

            val state1 = View.GONE
            progressState.postValue(state1)
        }
    }

    suspend fun getMarvelChars(offset: Int, limit: Int){
        progressState.postValue(View.VISIBLE)
        val newItems=MarvelLogic().getAllMarvelChars(offset,limit)
        items.postValue(newItems)
        progressState.postValue(View.GONE)
    }




}