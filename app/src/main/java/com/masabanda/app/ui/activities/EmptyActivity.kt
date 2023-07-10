package com.masabanda.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.masabanda.app.R
import com.masabanda.app.databinding.ActivityEmptyBinding
import com.masabanda.app.ui.fragments.FirstFragment
import com.masabanda.app.ui.fragments.SecondFragment
import com.masabanda.app.ui.fragments.ThirdFragment

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    fun iniciar() {
        var name:String=""
        intent.extras.let {
            // name= it?.getString("var1")!!

        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val frag=FirstFragment()
            val frag2= SecondFragment()
            val frag3= ThirdFragment()
            when(item.itemId) {
                R.id.item_1 -> {

                    // Respond to navigation item 1 click

                    val transaccion=supportFragmentManager.beginTransaction()
                    supportFragmentManager.popBackStack()
                    transaccion.add(binding.frmContainer.id,frag)
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                    true
                }
                R.id.item_2 -> {

                    // Respond to navigation item 1 click

                    val transaccion=supportFragmentManager.beginTransaction()
                    supportFragmentManager.popBackStack()
                    transaccion.add(binding.frmContainer.id,frag2)
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                    true
                }
                R.id.item_3 -> {

                    // Respond to navigation item 1 click

                    val transaccion=supportFragmentManager.beginTransaction()
                    supportFragmentManager.popBackStack()
                    transaccion.add(binding.frmContainer.id,frag3)
                    transaccion.addToBackStack(null)
                    transaccion.commit()
                    true
                }
                else -> false
            }
        }
    }
}