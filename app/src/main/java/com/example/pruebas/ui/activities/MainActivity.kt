package com.example.pruebas.ui.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    var userImageSelected = false


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        binding.btnIngresar.setOnClickListener{
            signInWithEmailAndPassword(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
            var intent = Intent(this, EmptyActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegistrar.setOnClickListener{
            authWithFirebaseEmail(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
        }


        binding.btnFacebook.setOnClickListener{
            val intent= Intent(Intent.ACTION_VIEW,
                Uri.parse("https://es-la.facebook.com/"))
            startActivity(intent)
        }


        binding.btnTwitter.setOnClickListener{
            val intent= Intent(Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/?lang=es"))
            startActivity(intent)
        }



    }

    private fun authWithFirebaseEmail(email :  String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(Constans.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication exitosa",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {
                    Log.w(Constans.TAG, "Fallo en la creacion de usuarios", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun signInWithEmailAndPassword(email :  String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(Constans.TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                } else {
                    Log.w(Constans.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun recoveryPasswordWithEmail(email :  String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Toast.makeText(
                        baseContext,
                        "Correo de verificacion enviado",
                        Toast.LENGTH_SHORT,
                    ).show()

                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Alert")
                        setMessage("Correo de recuperacion enviado correctamente")
                        setCancelable(true)
                    }.show()
                }

            }
    }


    override fun onStart() {
        super.onStart()
        //initClass()

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

/*

        //Face
        binding.btnFacebook.setOnClickListener{
//            val resIntent=Intent(this,ResultActivity::class.java)
//            appResultLocal.launch(resIntent)

//            //Reconocer la voz
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
        }

 */

        /*
        //Twitter
        binding.btnTwitter.setOnClickListener{
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
        }

         */

    }

    override fun onPause() {
        super.onPause()

    }

}

