package com.example.pruebaxd.ui.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.pruebaxd.R
import com.example.pruebaxd.databinding.ActivityEmptyBinding
import com.example.pruebaxd.ui.fragment.FirstFragment
import com.example.pruebaxd.ui.fragment.SecondFragment
import com.example.pruebaxd.ui.fragment.ThirdFragment

import com.example.pruebaxd.ui.utilities.FragmentsManager


class EmptyActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var name: String = " "
//        intent.extras.let{
//            name=it?.getString("var1")!!
//        }
//        Log.d("UCE", "Hola ${name}")
//        binding.textView.text = "Bienvenida " + name.toString()
//        Log.d("UCE", "Entrando a Start")
//        binding.back.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
        initClass()
    }

    private fun initClass() {
//        binding.back.setOnClickListener {
//            var intent = Intent(
//                this,
//                MainActivity::class.java
//            )
//        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )
                    true
                }

                R.id.fav -> {
                    FragmentsManager().replaceFragment(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        SecondFragment()
                    )
                    true
                }

                R.id.api -> {
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





