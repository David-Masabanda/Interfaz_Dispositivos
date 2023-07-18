package com.example.pruebas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebas.R
import com.example.pruebas.data.entities.marvel.MarvelChars
import com.example.pruebas.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var items:List<MarvelChars>,
    private var fnClick:(MarvelChars)->Unit
):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder (view: View) : RecyclerView.ViewHolder(view){

        private val binding: MarvelCharactersBinding=MarvelCharactersBinding.bind(view)

        fun render(item : MarvelChars, fnClick:(MarvelChars)->Unit){
//            println("Recibiendo a ${item.nombre}")
            Picasso.get().load(item.imagen).into(binding.imagen)
            binding.nombre.text=item.nombre
            binding.comic.text=item.comic

            //Para mostrar el nombre del personaje
//            binding.tarjeta.setOnClickListener{
//                Snackbar.make(binding.imagen, item.nombre, Snackbar.LENGTH_SHORT).show()
//            }

            //itemView se refiere a cualquier parte del elemento
            itemView.setOnClickListener{
                fnClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelAdapter.MarvelViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate( R.layout.marvel_characters, parent, false))
    }

    override fun getItemCount(): Int=items.size

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position],fnClick)
    }

    fun updateListItems(newItems: List<MarvelChars>){
        this.items=this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListItemsAdapter(newItems: List<MarvelChars>){
        this.items=newItems
        notifyDataSetChanged()
    }

}