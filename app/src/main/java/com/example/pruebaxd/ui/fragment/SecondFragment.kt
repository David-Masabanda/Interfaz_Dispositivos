package com.example.pruebaxd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.pruebaxd.R
import com.example.pruebaxd.data.entities.marvel.MarvelChars
import com.example.pruebaxd.databinding.FragmentSecondBinding
import com.example.pruebaxd.ui.activities.DetailsMarvelItem
import com.example.pruebaxd.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
    private  var rvAdapter: MarvelAdapter = MarvelAdapter({ sendMarvelItems(it) }, { saveMarvelItem(it) })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        progressBar = binding.progressBar
        return binding.root
    }
    override fun onStart() {
        super.onStart()

        binding.txtBucar.addTextChangedListener{filteredText->
            Log.d("PROBANDO",filteredText.toString())
            if(filteredText.toString().isNotEmpty()){
                reset()
                chargeDataRV(filteredText.toString())

            }
            else{
                reset()
            }
        }
    }
    fun reset(){
        marvelCharacterItems=mutableListOf<MarvelChars>()
        rvAdapter.replaceListAdapter(marvelCharacterItems)
    }
    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("image", item)
        startActivity(i)
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {
        return if (item == null || marvelCharacterItems.contains(item)) {
            false
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    MarvelLogic().insertMarvelCharstoDB(listOf(item))
                    marvelCharacterItems = MarvelLogic().getAllCharactersDB().toMutableList()
                }

            }
            true
        }

    }
    private fun chargeDataRV(nombre:String) {

        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getAllCharacters(nombre,5))
            } as MutableList<MarvelChars>
            if(marvelCharacterItems.size==0){
                var f= Snackbar.make(binding.txtBucar, "No se encontró", Snackbar.LENGTH_LONG)
                f.show()
            }
            rvAdapter.items =
                MarvelLogic().getAllCharacters(nombre ,5)
            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE
        }
    }

}