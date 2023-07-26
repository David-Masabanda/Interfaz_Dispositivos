package com.example.pruebas.ui.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityMainBinding
import com.example.pruebas.logic.LoginValidator
import com.example.pruebas.ui.utilities.MyLocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Ubicacion y GPS
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : LocationCallback
    private var currentLocation : Location? =null

    //Pide al servicio que solicite el permiso de alta precision
    private lateinit var client : SettingsClient
    private lateinit var locationSettingRequest : LocationSettingsRequest


    val speechToText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
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
                sn.setBackgroundTint(resources.getColor(R.color.blue))
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

    @SuppressLint("MissingPermission")
    val locationContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->
        when(isGranted){
            true->{

                client.checkLocationSettings(locationSettingRequest).apply {
                    addOnSuccessListener {
                        val task = fusedLocationProviderClient.lastLocation
                        task.addOnSuccessListener {location->
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.getMainLooper()
                            )
                        }
                    }

                    //Cuando me da un error
                    addOnFailureListener{ex->
                        if (ex is ResolvableApiException){
//                            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                            ex.startResolutionForResult(
                                this@MainActivity,
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                            )
                        }
                    }
                }




//                val alert = AlertDialog.Builder(this).apply {
//                    setTitle("Notificacion")
//                    setMessage("Por favor verifique que el GPS este activo")
//                    setPositiveButton("Verificar"){dialog, id->
//
//                        val i=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                        startActivity(i)
//                        dialog.dismiss()
//                    }
//                    setCancelable(false)
//                }.show()



//                task.addOnFailureListener{
//                    val alert = AlertDialog.Builder(
//                        this, com.google.android.material.R.style.Base_ThemeOverlay_AppCompat )
//                    alert.apply {
//                        setTitle("Alerta")
//                        setMessage("Existe un problema con el sistema de posicionamiento global")
//                        setPositiveButton("OK"){dialog, id -> dialog.dismiss()}
//                        setCancelable(false)
//                    }.create()
//                    alert.show()
//                }



            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)->{

            }
            false->{
                Snackbar.make(binding.imageView, "Permiso denegado", Snackbar.LENGTH_SHORT).show()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,1000
        ).build()

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult : LocationResult) {
                super.onLocationResult(locationResult)

                if(locationResult!=null){
                    locationResult.locations.forEach{ location->
                        currentLocation = location
                        Log.d("UCE", "Ubicacion: ${location.latitude},"+"${location.longitude}")
                    }
                }
            }
        }

        client = LocationServices.getSettingsClient(this)
        locationSettingRequest= LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()
    }




    override fun onStart() {
        super.onStart()
        initClass()

        //Importar el Register
        //Es un contrato y las clausulas que debera comprobar cuando se lance ese contrato
        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultActivity ->
            val sn=Snackbar.make(binding.imageView, "", Snackbar.LENGTH_LONG)

            var color :Int = resources.getColor(R.color.black)

            var message = when(resultActivity.resultCode){
                RESULT_OK->{
                    sn.setBackgroundTint(resources.getColor(R.color.blue))
                    resultActivity.data?.getStringExtra("result").orEmpty()}
                RESULT_CANCELED->{
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                    resultActivity.data?.getStringExtra("result").orEmpty()}
                else->{ "Testeo dudoso"}
            }
            sn.setText(message)
            sn.show()
        }



        //Face
        binding.imageButton.setOnClickListener{
//            val resIntent=Intent(this,ResultActivity::class.java)
//            appResultLocal.launch(resIntent)

//            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            intentSpeech.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE,
//                Locale.getDefault()
//            )
//            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                "Di algo...")
//
//            //La estructura debe ser lineal no puedo acceder a partes que se declaran despues
//            speechToText.launch(intentSpeech)

            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }

        //Twitter
        binding.imageButton2.setOnClickListener{
//            val intent= Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://twitter.com/i/flow/login?redirect_after_login=%2F%3Flang%3Des"))

//            val intent = Intent(
//                Intent.ACTION_WEB_SEARCH
//            )
//            intent.setClassName(
//                "com.google.android.googlequicksearchbox",
//                "com.google.android.googlequicksearchbox.SearchActivity"
//            )
//            intent.putExtra(SearchManager.QUERY, binding.editTextTextPassword.text)
//            startActivity(intent)

            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }



    }

    private fun initClass() {
        binding.btnIngresar.setOnClickListener {
            val name = binding.txtUser.text.toString()
            Log.d("EmptyActivity", "El nombre de usuario es: $name")
            val password = binding.editTextTextPassword.text.toString()
            Log.d("EmptyActivity", "La clave de usuario es: $password")

            val check = LoginValidator().checkLogin(name, password)
            if (check) {
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(name)
                }
                var intent = Intent(this, EmptyActivity::class.java)
                intent.putExtra("nameUser", name)
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.root,
                    "Usuario y contraseña inválidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }


    private suspend fun saveDataStore(stringData: String){
        dataStore.edit { prefs->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("sesion")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "jdmasabanda@uce.edu.com"
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun test(){
        var location = MyLocationManager(this)
        location.getUserLocation()
    }


}

