package com.example.pruebas.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebas.R
import com.example.pruebas.logic.data.MarvelChars
import com.example.pruebas.databinding.FragmentFirstBinding
import com.example.pruebas.logic.jikanLogic.JikanAnimeLogic
import com.example.pruebas.logic.marvelLogic.MarvelLogic
import com.example.pruebas.ui.activities.DetailsMarvelItem
import com.example.pruebas.ui.activities.dataStore
import com.example.pruebas.ui.adapters.MarvelAdapter
import com.example.pruebas.ui.data.UserDataStore
import com.example.pruebas.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var gmanager: GridLayoutManager
    private lateinit var marvelCharsItems: MutableList<MarvelChars>

    private val limit=99
    private var offset=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFirstBinding.inflate(layoutInflater,container,false)
        gmanager= GridLayoutManager(requireActivity(),2)
        lmanager=LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asGif()
            .load(R.drawable.rocket)
            .into(binding.gifMarvel);

        chargeDataInit(offset, limit)

        binding.rvSwipe.setOnRefreshListener {
            refreshData()
        }

        binding.rvMarvel.addOnScrollListener (object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy>0){
                    val v=lmanager.childCount
                    val p=lmanager.findFirstCompletelyVisibleItemPosition()
                    val t=lmanager.itemCount

                    if ((v+p)>=t){
//                        loadMoreData()
                    }

                }
            }
        })


    }

    private fun refreshData() {
        // Reiniciar el offset
        offset = 0
        // Limpiar la lista actual
        marvelCharsItems.clear()
        // Cargar los nuevos datos desde la API
        chargeDataInit(offset, limit)
        // Finalizar el refresh
        binding.rvSwipe.isRefreshing = false
    }

    private fun loadMoreData() {
        val newOffset = offset + limit

        lifecycleScope.launch(Dispatchers.IO) {
            val newItems = MarvelLogic().getAllMarvelChars(newOffset, limit)
            withContext(Dispatchers.Main) {
                marvelCharsItems.addAll(newItems)
                rvAdapter.notifyDataSetChanged()
                offset = newOffset
            }
        }

        // Llama a chargeDataAPI para cargar más datos con el nuevo offset
        chargeDataAPI(newOffset, limit)
    }




    fun chargeDataInit( offset: Int,limit: Int,){
        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO){
                return@withContext MarvelLogic().getAllMarvelChars(offset,limit)
            }

            rvAdapter = MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }
    }

    fun chargeDataAPI(offset: Int,limit: Int){
        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO){
                return@withContext MarvelLogic().getAllMarvelChars(offset,limit)
            }

            rvAdapter = MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            this@FirstFragment.offset+=limit
        }
    }







    fun sendMarvelItem(item: MarvelChars){
        val i= Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("imagen", item)
        i.putExtra("desc", item)
        startActivity(i)
    }


}