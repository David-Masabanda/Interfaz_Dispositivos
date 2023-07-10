package com.masabanda.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.masabanda.app.databinding.ActivityDetailsMarvelItemBinding
import com.masabanda.app.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        val item=intent.getParcelableExtra<MarvelChars>("name")
        if(item!=null){
            binding.txtNombre.text=item.name
            binding.txtComic.text=item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)


        }
    }
}