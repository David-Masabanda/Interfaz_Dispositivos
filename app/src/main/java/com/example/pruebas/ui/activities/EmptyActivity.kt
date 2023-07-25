package com.example.pruebas.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityEmptyBinding
import com.example.pruebas.databinding.ActivityMainBinding
import com.example.pruebas.ui.fragments.FirstFragment
import com.example.pruebas.ui.fragments.SecondFragment
import com.example.pruebas.ui.fragments.ThirdFragment
import com.example.pruebas.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameUser = intent.getStringExtra("nameUser")
        val welcomeMessage = "¡Bienvenido, $nameUser!"

        if(!nameUser.isNullOrEmpty()){
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()
        }else{
            Snackbar.make(binding.txtSaludo, "Fallo en la verificacion", Snackbar.LENGTH_SHORT).show()
        }

        Log.d("UCE", "Empty Activity")
    }

    override fun onStart() {
        super.onStart()

//        FragmentsManager().replaceFragment(
//            supportFragmentManager,
//            binding.frmContainer.id,
//            FirstFragment()
//        )

        initClass()
    }

    private fun initClass() {

//        val nameUser = intent.getStringExtra("nameUser")
//        binding.txtSaludo.text = "Bienvenido, $nameUser"


        binding.btnRegreso.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.option_1 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }
                R.id.option_2 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    true
                }
                R.id.option_3 -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )
                    true
                }
                else -> false
            }
        }

    }
}