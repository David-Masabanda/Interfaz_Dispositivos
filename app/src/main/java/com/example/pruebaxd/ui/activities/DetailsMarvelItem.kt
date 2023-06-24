package com.example.pruebaxd.ui.activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebaxd.R
import com.example.pruebaxd.data.marvel.MarvelPersonajes
import com.example.pruebaxd.databinding.ActivityDetailsMarvelItemBinding
import com.example.pruebaxd.databinding.MarvelCharactersBinding
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

        //Version anterior luego hicimos el data class serializable

//        var name:String?=""
//        var comic:String?=""

//        intent.extras?.let {
//            name=it.getString("name")
//            comic=it.getString("comic")
//            imagen=it.getString("imagen")
//        }
//        if (!name.isNullOrEmpty()){
//           binding.textoMarvel.text=name
//
//        }
//
//        if (!comic.isNullOrEmpty()){
//            binding.comicMarvel.text=comic
//        }

        //especifico que regrese a la forma q tenia con el <...>
        val item=intent.getParcelableExtra<MarvelPersonajes>("name")
        if (item!=null){
            binding.textoMarvel.text=item.nombre
            binding.comicMarvel.text=item.comic
            Picasso.get().load(item.imagen).into(binding.imagenMarvel)
        }



    }

}