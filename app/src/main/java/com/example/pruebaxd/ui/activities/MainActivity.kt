package com.example.pruebaxd.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.pruebaxd.R
import com.example.pruebaxd.logic.validator.LoginValidator
import com.example.pruebaxd.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para darle un contexto la inicializamos aqui
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        initClass()


        //Importar el Register
        //Es un contrato y las clausulas que debera comprobar cuando se lance ese contrato
        val appResultLocal = registerForActivityResult(StartActivityForResult()){ resultActivity ->

            val sn=Snackbar.make(binding.imageView, "", Snackbar.LENGTH_LONG)


            var color :Int = resources.getColor(R.color.black)

            var message = when(resultActivity.resultCode){
                RESULT_OK->{
                    sn.setBackgroundTint(resources.getColor(R.color.celeste2))
                    resultActivity.data?.getStringExtra("result").orEmpty() }
                RESULT_CANCELED->{
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                    resultActivity.data?.getStringExtra("result").orEmpty()}
                else->{"Testeo dudoso"}
            }

            sn.setText(message)
            sn.show()


        }


        val speechToText = registerForActivityResult(StartActivityForResult()){activityResult->
            val sn=Snackbar.make(binding.imageView, "", Snackbar.LENGTH_LONG)
            var message=""
            when(activityResult.resultCode){
                RESULT_OK->{

                    //Devuelve el texto de voz
                    val msg = activityResult
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()

                    //Para hacer una consulta con la voz
                    //Borrar el white data del emulador en tools
                    if(msg.isNotEmpty()){
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }



                    sn.setBackgroundTint(resources.getColor(R.color.teal_200))
                }
                RESULT_CANCELED->{
                    message="Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))}
                else->{
                    message="Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }

            }

            sn.setText(message)
            sn.show()
        }


        //Ahora necesito llamar a un PermissionResult porque voy a pedir
        val locationContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
         isGranted->

            when(isGranted){
                true->{
                    //Agregar la anotacion al initclass
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {

//                        if (task.result!=null){
//                            Snackbar.make(binding.titulo, "${it.latitude},${it.longitude}", Snackbar.LENGTH_SHORT).show()
//
//                        }else{
//                            Snackbar.make(binding.titulo, "Encienda el GPS", Snackbar.LENGTH_SHORT).show()
//                        }

//                        it.longitude
//                        it.latitude

                        val a = Geocoder(this)
                        a.getFromLocation(it.latitude,it.longitude,1)
                    }

                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)->
//
//                    shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)->{
//                        Snackbar.make(binding.imageView, "Dame el permiso o tienes 0", Snackbar.LENGTH_SHORT).show()
//                    }
                }

                false->{Snackbar.make(binding.imageView, "Permiso denegado", Snackbar.LENGTH_SHORT).show()}

            }

        }



        //fACE
        binding.imageButton5.setOnClickListener{
//            val resIntent=Intent(this,ResultActivity::class.java)
//            appResultLocal.launch(resIntent)

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Di algo...")

            //La estructura debe ser lineal no puedo acceder a partes que se declaran despues
            speechToText.launch(intentSpeech)

        }

        //tWITTER
        binding.imageButton6.setOnClickListener{
//            val intent= Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://twitter.com/i/flow/login?redirect_after_login=%2F%3Flang%3Des"))

            //Para buscar segun un texto
//            val intent = Intent(
//                Intent.ACTION_WEB_SEARCH
//            )
//            intent.setClassName(
//                "com.google.android.googlequicksearchbox",
//                "com.google.android.googlequicksearchbox.SearchActivity"
//            )
//            intent.putExtra(SearchManager.QUERY, binding.correoEjemplo.text)
//            startActivity(intent)

            //De esta forma referenciamos al manifest general
            //locationContract.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }




    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun initClass() {
        binding.botonIngresar.setOnClickListener {
            val check= LoginValidator().checkLogin(
                binding.correoEjemplo.text.toString(),
                binding.correoPassword.text.toString()
            )
            if (check){
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore( binding.correoEjemplo.text.toString())
                }

                var intent = Intent(this,EmptyActivity::class.java)
                intent.putExtra("var1",binding.correoEjemplo.text.toString())
                startActivity(intent)
            }
            else {
                Snackbar.make(
                    binding.correoEjemplo , "Usuario y contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private suspend fun  saveDataStore(stringData: String){
        dataStore.edit {prefs->
            prefs[stringPreferencesKey("usuario")]=   stringData
            prefs[stringPreferencesKey("sesion")]=   UUID.randomUUID().toString()

        }
    }



}

