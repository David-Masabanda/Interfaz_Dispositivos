package com.example.pruebaxd.ui.adapters


import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaxd.R


import com.example.pruebaxd.data.entities.marvel.MarvelChars
import com.example.pruebaxd.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar

import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave: (MarvelChars) -> Boolean
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items:List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)
        fun render(item: MarvelChars,
                   fnClick: (MarvelChars) -> Unit,
                   fnSave: (MarvelChars) -> Boolean
        ) {
            binding.txtTitulo.text = item.nombre
            binding.txtComic.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imgMarvel)

            itemView.setOnClickListener {
                fnClick(item)
            }

            binding.imgMarvel.setOnClickListener {
                var checkInsert:Boolean=false
                checkInsert=fnSave(item)
                if(checkInsert){
                    Snackbar.make(
                        binding.imgMarvel,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }else{
                    Snackbar.make(
                        binding.imgMarvel,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(
            R.layout.marvel_characters,
            parent,
            false))
    }

    override fun getItemCount(): Int =items.size

    fun updateListItems(newItems: List<MarvelChars>){
        this.items =this.items.plus(newItems)
        notifyDataSetChanged()

    }
    fun replaceListAdapter(newItems: List<MarvelChars>) {
        this.items = newItems
        notifyDataSetChanged()

    }


    fun replaceListItems(newItems: List<MarvelChars>) {
        this.items = newItems
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick, fnSave)
    }
}