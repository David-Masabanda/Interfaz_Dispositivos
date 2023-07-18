package com.example.pruebas.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnInicio.setOnClickListener{
            //Con la funcion setResult enviamos el resultado final
            setResult(RESULT_OK)
            //Con esto logramos que la activity finalice
            finish()
        }

        binding.btnResult.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}