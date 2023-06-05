package com.example.pruebaxd.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pruebaxd.databinding.ActivityEmptyBinding
import com.google.android.material.snackbar.Snackbar

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando a Create")
        binding=ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onStart() {
        super.onStart()
        var name:String=""
        intent.extras.let {
            // it.toString()
            name= it?.getString("var2")!!
        }
        Log.d("UCE","Hola${name}")
        binding.textView.text="Bienvenido "+name.toString()
        Log.d("UCE","Entrando a Start")

        initClass()
    }
    private fun initClass(){
        binding.button3.setOnClickListener{
            /*
            var f = Snackbar.make(
                binding.button1,
                "EmptyActivity",
                Snackbar.LENGTH_SHORT
            )
                .show()
                */

            var intent = Intent(this,MainActivity::class.java
            )
            //   intent.putExtra("var1","Juan")
            startActivity(intent)
        }
    }
}