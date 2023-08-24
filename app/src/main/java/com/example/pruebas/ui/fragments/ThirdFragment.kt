package com.example.pruebas.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.pruebas.R
import com.example.pruebas.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding



    val pickMedia=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        uri->
        if(uri!=null){
            binding.userImagen.setImageURI(uri)

        }else{
            Log.i("UCE","No seleccionado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(
            layoutInflater, container, false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asGif()
            .load(R.drawable.esperando)
            .into(binding.gifImageView);


        binding.btnCambiarImg.setOnClickListener {
            val options = arrayOf("Tomar foto", "Seleccionar de la galería")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Elegir una opción")
            builder.setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        // Tomar foto
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraResult.launch(intent)
                    }
                    1 -> {
                        // Seleccionar de la galería
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
            }

            val dialog = builder.create()
            dialog.show()
        }


    }

    private val cameraResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        when(result.resultCode){
            Activity.RESULT_OK->{
                val image =  result.data?.extras?.get("data") as Bitmap
                binding.userImagen.setImageBitmap(image)
            }
            Activity.RESULT_CANCELED->{}
        }
    }







}