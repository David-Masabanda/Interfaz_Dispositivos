package com.example.pruebaxd.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebaxd.data.entities.marvel.MarvelChars
import com.example.pruebaxd.databinding.ActivityDetailsMarvelItemBinding


import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {


    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val item = intent.getParcelableExtra<MarvelChars>("name")
        if (item != null) {
            binding.textoCentrado.text = item.nombre
            binding.txtComic.text = item.comic
//            binding.txtDescripcion.text=item.descripcion
            Picasso.get().load(item.imagen).into(binding.imgPersonaje)

        }
    }
}