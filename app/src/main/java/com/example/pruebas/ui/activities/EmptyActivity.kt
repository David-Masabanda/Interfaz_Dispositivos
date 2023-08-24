package com.example.pruebas.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        val welcomeMessage = "¡Bienvenido!"
        Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()
        Log.d("UCE", "Empty Activity")
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    private fun initClass() {

        binding.btnRegreso.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when(item.itemId) {

                R.id.option_1 -> {
                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        ThirdFragment()
                    )
                    binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    true
                }
                R.id.option_2 -> {
                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    true
                }
                R.id.option_3 -> {
                    hideLayout()
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    true
                }
                else -> false
            }
        }


    }

    private fun hideLayout() {
        binding.txtSaludo.visibility = View.GONE
        binding.txtMensaje.visibility = View.GONE
        binding.imgEspera.visibility = View.GONE
        binding.btnRegreso.visibility = View.GONE

    }

    private fun showLayout() {
        binding.txtSaludo.visibility = View.VISIBLE
        binding.txtMensaje.visibility = View.VISIBLE
        binding.imgEspera.visibility = View.VISIBLE
        binding.btnRegreso.visibility = View.VISIBLE
        binding.frmContainer.visibility = View.VISIBLE
    }
}