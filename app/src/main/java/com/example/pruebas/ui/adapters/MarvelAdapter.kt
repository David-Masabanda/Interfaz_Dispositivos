package com.example.pruebas.ui.adapters

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebas.R
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.databinding.MarvelCharactersBinding
import com.example.pruebas.logic.data.getMarvelCharsDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var items:List<MarvelChars>,
    private var fnClick:(MarvelChars)->Unit
):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder (view: View) : RecyclerView.ViewHolder(view){

        private val binding: MarvelCharactersBinding=MarvelCharactersBinding.bind(view)

        private lateinit var mediaPlayer: MediaPlayer
        init {
            mediaPlayer = MediaPlayer.create(itemView.context, R.raw.click)
        }



        fun render(item : MarvelChars, fnClick:(MarvelChars)->Unit){
            Picasso.get().load(item.imagen).into(binding.imagen)
            binding.nombre.text=item.nombre
            binding.comic.text=item.comic


            //itemView se refiere a cualquier parte del elemento
            itemView.setOnClickListener{
                fnClick(item)
            }

            binding.favorito.setOnClickListener {
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    val userDocRef = FirebaseFirestore.getInstance().collection("users").document(user.uid)
                    userDocRef.update("favoriteChar", FieldValue.arrayUnion(item.getMarvelCharsDB()))
                        .addOnSuccessListener {
                            Log.d("UCE", "Personaje guargado en Firestore")
                        }
                        .addOnFailureListener { e ->
                            Log.d("UCE", "No se pudo guardar el personaje")
                        }
                }

                mediaPlayer.start()
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