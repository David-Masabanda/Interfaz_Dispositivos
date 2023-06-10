package com.example.pruebaxd.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import com.example.pruebaxd.R
import com.example.pruebaxd.databinding.ActivityEmptyBinding
import com.example.pruebaxd.ui.fragments.FirstFragment
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
           // name= it?.getString("var2")!!
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
        binding.bottomNavigation.setOnItemSelectedListener {
                item ->
            when(item.itemId) {
                R.id.option_1 -> {
                    // Respond to navigation item 1 click
//                    binding.button3.text="Hola"
//                    binding.textView.text="Estatus"

                    val frag=FirstFragment()
                    val transacction=supportFragmentManager.beginTransaction()
                    transacction.add(binding.frm.id, frag)
                    transacction.addToBackStack(null)
                    transacction.commit()

                    true
                }
                R.id.option_2 -> {
                    // Respond to navigation item 2 click
                    Snackbar.make(
                        binding.bottomNavigation,
                        "Esta Vivo",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    true
                }
                R.id.option_3 -> {
                    // Respond to navigation item 2 click
                    var intent = Intent(this,MainActivity::class.java
                    )
                    //   intent.putExtra("var1","Juan")
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}