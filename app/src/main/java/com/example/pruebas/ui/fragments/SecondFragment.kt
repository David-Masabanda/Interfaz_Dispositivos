package com.example.pruebas.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebas.R
import com.example.pruebas.databinding.FragmentSecondBinding
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.logic.marvelLogic.MarvelLogic
import com.example.pruebas.ui.activities.DetailsMarvelItem
import com.example.pruebas.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
    private var rvAdapter: MarvelAdapter = MarvelAdapter(marvelCharacterItems){ sendMarvelItems(it) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(
            layoutInflater, container, false
        )
        lmanager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        progressBar=binding.progressBar
        return binding.root
    }

    override fun onStart() {
        super.onStart()
//        val nombres = arrayListOf<String>("Sebitas", "Jaime", "Tamarita", "Deivid", "Luci")
//        val adapter= ArrayAdapter<String>(
//            requireActivity(),
//            /*android.R.layout.simple_spinner_item, */
//            R.layout.simple_layout,
//            nombres)
//        binding.spinn.adapter=adapter
//        binding.list2.adapter=adapter

    binding.txtBuscar.addTextChangedListener { filteredText->
        if (filteredText.toString().isNotEmpty()){
            reset()
            chargeDataRV(filteredText.toString())
        }else{
            reset()
        }
    }


    }

    fun reset(){
        marvelCharacterItems=mutableListOf<MarvelChars>()
        rvAdapter.replaceListItemsAdapter(marvelCharacterItems)
    }

    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("image", item)
        startActivity(i)
    }

    private fun chargeDataRV(nombre:String) {

        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getMarvelChars(nombre,5))
            }
            if(marvelCharacterItems.size==0){

                var f= Snackbar.make(binding.txtBuscar, "No se encontró", Snackbar.LENGTH_LONG)
                f.show()
            }
            rvAdapter = MarvelAdapter(marvelCharacterItems){sendMarvelItems(it)}
            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE
        }
    }


}