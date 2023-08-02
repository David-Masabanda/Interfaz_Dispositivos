package com.example.pruebas.ui.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityProgressBinding
import com.example.pruebas.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private val progressviewmodel by viewModels<ProgressViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Livedata
        progressviewmodel.progressState.observe(this) { binding.progBar.visibility = it }
        progressviewmodel.items.observe(this) {
            Toast.makeText(
                this,
                it[0].nombre,
                Toast.LENGTH_SHORT
            ).show()
        }
        //Puedo cambiar a otra activity
//        {startActivity(Intent(this, CameraActivity::class.java))}

        //Listeners
        binding.btnProceso1.setOnClickListener{
            progressviewmodel.processBackground(6000)

        }

        binding.btnProceso2.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                progressviewmodel.getMarvelChars(0,90)
            }

        }
    }
}