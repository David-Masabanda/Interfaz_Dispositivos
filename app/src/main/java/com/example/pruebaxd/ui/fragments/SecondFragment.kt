package com.example.pruebaxd.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pruebaxd.R
import com.example.pruebaxd.data.entities.jikan.JikanAnimeEntity
import com.example.pruebaxd.data.marvel.MarvelPersonajes
import com.example.pruebaxd.databinding.FragmentSecondBinding
import com.example.pruebaxd.logic.JikanLogic.JikanAnimeLogic
import com.example.pruebaxd.logic.marvelLogic.MarvelLogic
import com.example.pruebaxd.logic.validator.ListItems
import com.example.pruebaxd.ui.activities.DetailsMarvelItem
import com.example.pruebaxd.ui.activities.MainActivity
import com.example.pruebaxd.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    private lateinit var lmanager:LinearLayoutManager
    private lateinit var rvAdapter:MarvelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(
            layoutInflater, container, false
        )
        lmanager=LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val nombres = arrayListOf<String>("Sebitas", "Jaime", "Tamarita", "Deivid", "Luci")

        val adapter=ArrayAdapter<String>(
            requireActivity(),
           /*android.R.layout.simple_spinner_item, */
            R.layout.simple_layout,
            nombres)

//       // binding.spinn.adapter=adapter
//        //binding.list2.adapter=adapter
//
//        val rvAdapter=MarvelAdapter(
//            ListItems().returnMarvelChars()
//        ) { sendMarvelItem(it) }
//
//
//        val rvMarvel=binding.rvMarvel
//        with(rvMarvel){
//            rvMarvel.adapter=rvAdapter
//            rvMarvel.layoutManager=LinearLayoutManager(
//                requireActivity(),
//                LinearLayoutManager.VERTICAL,
//                false)
//
//        }

        chargeDataRV("cap")


        binding.rvSwipe.setOnRefreshListener {

            chargeDataRV("cap")
            binding.rvSwipe.isRefreshing=false
        }

        binding.rvMarvel.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy>0){
                    //cuantos han pasado
                    val v=lmanager.childCount
                    //posicion
                    val p= lmanager.findFirstCompletelyVisibleItemPosition()
                    //cuantos tengo en total
                    val t=lmanager.itemCount

                    if((v+p)>=t){
                        lifecycleScope.launch(Dispatchers.IO){
                            val newItems=JikanAnimeLogic().getAllAnimes()
//                            val newItems=MarvelLogic().getAllAnimes("electra",50)
                            withContext(Dispatchers.Main){
                                rvAdapter.updateListItems(newItems)
                            }
                        }
                    }
                }

            }
        })

    }

    //Debe ser llamada desde el adaptador
    //Va ser enviado como lambda
    fun sendMarvelItem(item: MarvelPersonajes){
        val i=Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("imagen", item)
        startActivity(i)
    }

//    fun corrutine(){
//        lifecycleScope.launch(Dispatchers.Main){
//            var name="Antonio"
////            lifecycleScope.launch(Dispatchers.IO){
////                name="Joss"
////                //Son 2 espacios complemente distintos, no se da el cambio
////            }
//            name= withContext(Dispatchers.IO){
//                name="Joss"
//                return@withContext name
//                //Lo arreglamos de esta forma
//            }
//
//
//        }
//    }

    fun chargeDataRV(search:String){

        lifecycleScope.launch(Dispatchers.IO){

            //no me srive mucho porq reemplaza la lista no la añade
             rvAdapter=MarvelAdapter(
//            ListItems().returnMarvelChars()
                 JikanAnimeLogic().getAllAnimes()

//                MarvelLogic().getAllAnimes(search,50)
            ) { sendMarvelItem(it) }

            withContext(Dispatchers.Main){
                with(binding.rvMarvel){
                    this.adapter=rvAdapter
                    this.layoutManager=lmanager

                }
            }

        }


    }


}