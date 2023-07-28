package com.example.pruebas.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCaptura.setOnClickListener{
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(i)
        }

        binding.imgPersona.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hola como estas")
            shareIntent.setType("text/plain")
            startActivity(Intent.createChooser(shareIntent, "Compartir"))
        }

    }

    private val cameraResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        when(result.resultCode){
            Activity.RESULT_OK->{
                val image =  result.data?.extras?.get("data") as Bitmap
                binding.imgPersona.setImageBitmap(image)
            }
            Activity.RESULT_CANCELED->{}
        }
    }
}