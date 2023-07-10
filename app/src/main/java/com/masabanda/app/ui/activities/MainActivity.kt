package com.masabanda.app.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.masabanda.app.R
import com.masabanda.app.databinding.ActivityMainBinding
import com.masabanda.app.logic.validator.LoginValidator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando al onCreate")
        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClass()
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {
        Log.d("UCE","Entrando al onStart")
        binding.button.setOnClickListener {
            if(LoginValidator().checkLogin(binding.editTextTextEmailAddress.text.toString(),binding.editTextTextPassword.text.toString() )){
                var intent = Intent(this,EmptyActivity::class.java)
                intent.putExtra("var1","HOLA")
                startActivity(intent)
            }
            else{
                var f= Snackbar.make(binding.button, "matenme x2", Snackbar.LENGTH_LONG)

                f.show()
            }


        }

    }
}