package com.example.pruebas.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.pruebas.databinding.ActivityMainBinding
import com.example.pruebas.logic.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()


        //Importar el Register
        //Es un contrato y las clausulas que debera comprobar cuando se lance ese contrato
        val appResultLocal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultActivity ->
            when(resultActivity.resultCode){
                RESULT_OK->{ Snackbar.make(binding.imageView,"Testeo valido", Snackbar.LENGTH_LONG).show() }
                RESULT_CANCELED->{Snackbar.make(binding.imageView,"Testeo fallido", Snackbar.LENGTH_LONG).show()}
                else->{Log.d("UCE","Testeo dudoso")}
            }

        }

        binding.imageButton.setOnClickListener{
            val resIntent=Intent(this,ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }



        binding.imageButton2.setOnClickListener{
//            val intent= Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://twitter.com/i/flow/login?redirect_after_login=%2F%3Flang%3Des"))

            val intent = Intent(
                Intent.ACTION_WEB_SEARCH
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY, binding.editTextTextPassword.text)
            startActivity(intent)
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

}

