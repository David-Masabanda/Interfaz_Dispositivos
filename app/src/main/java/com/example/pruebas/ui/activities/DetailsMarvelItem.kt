package com.example.pruebas.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.databinding.ActivityDetailsMarvelItemBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()


        val item=intent.getParcelableExtra<MarvelChars>("name")
        if (item!=null){
            binding.nombreMarvel.text=item.nombre
            binding.comicMarvel.text=item.comic
            Picasso.get().load(item.imagen).into(binding.imagenMarvel)
            binding.resumenTexto.text=item.desc
        }
    }
}