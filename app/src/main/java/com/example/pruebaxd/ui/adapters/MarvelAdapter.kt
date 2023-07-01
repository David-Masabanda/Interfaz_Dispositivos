package com.example.pruebaxd.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaxd.R
import com.example.pruebaxd.data.marvel.MarvelPersonajes
import com.example.pruebaxd.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var items:List<MarvelPersonajes>,
    private var fnClick:(MarvelPersonajes)->Unit
    ):



    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    //Unit significa que es una funcion q no devuelve nada, como el void en un metodo de java
    class MarvelViewHolder (view: View) : RecyclerView.ViewHolder(view){

        private val binding:MarvelCharactersBinding=MarvelCharactersBinding.bind(view)

        fun render(item : MarvelPersonajes, fnClick:(MarvelPersonajes)->Unit){
//            println("Recibiendo a ${item.nombre}")


            Picasso.get().load(item.imagen).into(binding.imagen)
            binding.nombre.text=item.nombre
            binding.comic.text=item.comic

            //itemView se refiere a cualquier parte del elemento
            itemView.setOnClickListener{
               //De la clase anterior
                // Snackbar.make(binding.imagen, item.nombre, Snackbar.LENGTH_SHORT).show()
                fnClick(item)
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate( R.layout.marvel_characters, parent, false))
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position],fnClick)
    }

    override fun getItemCount(): Int=items.size

    fun updateListItems(newItems:List<MarvelPersonajes>){
        this.items=this.items.plus(newItems)
        notifyDataSetChanged()
    }


}