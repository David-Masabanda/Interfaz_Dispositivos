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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebas.R
import com.example.pruebas.data.entities.marvel.MarvelChars
import com.example.pruebas.databinding.FragmentFirstBinding
import com.example.pruebas.logic.jikanLogic.JikanAnimeLogic
import com.example.pruebas.logic.list.ListItems
import com.example.pruebas.logic.marvelLogic.MarvelLogic
import com.example.pruebas.ui.activities.DetailsMarvelItem
import com.example.pruebas.ui.activities.dataStore
import com.example.pruebas.ui.adapters.MarvelAdapter
import com.example.pruebas.ui.data.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var marvelCharsItems: MutableList<MarvelChars>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFirstBinding.inflate(layoutInflater,container,false)

        lmanager=LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false)

        return binding.root

    }

    override fun onStart() {
        super.onStart()

//        val rvAdapter=MarvelAdapter(ListItems().returnMarvelChars())
//        val rvMarvel=binding.rvMarvel
//
//        rvMarvel.adapter=rvAdapter
//
//        rvMarvel.layoutManager= LinearLayoutManager(
//            requireActivity(),
//            LinearLayoutManager.VERTICAL,
//            false)

        //DataStore
        lifecycleScope.launch(Dispatchers.IO){
            getDataStore().collect(){user->
                Log.d("UCE", user.name)
                Log.d("UCE", user.sesion)
                Log.d("UCE", user.email)
            }
            //Cosas que puedo hacer, como un filtro
            //getDataStore().filter{}.collect{}
        }


//        chargeDataRV()

        chargeDataRV2("Spider")

        binding.rvSwipe.setOnRefreshListener {
//            chargeDataRV()
            chargeDataRV2("Spider")
            binding.rvSwipe.isRefreshing=false
        }

        binding.rvMarvel.addOnScrollListener (object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy>0){
                    val v=lmanager.childCount
                    val p=lmanager.findFirstCompletelyVisibleItemPosition()
                    val t=lmanager.itemCount

                    if ((v+p)>=t){
                        lifecycleScope.launch(Dispatchers.IO){
                            //val newItems=JikanAnimeLogic().getAllAnimes()
                            val newItems=MarvelLogic().getAllChars("Hulk",10)
                            withContext(Dispatchers.Main){
                                rvAdapter.updateListItems(newItems)
                            }
                        }
                    }
                }
            }
        })

        //Aqui puedo hacer un filtro despues, necesito un Text.... en le layout
        //Debo importar el correcto textchanged { } crossinline Editable
        //no es rvMarvel sino el txtFilter o como lo tenga

//
//        binding.rvMarvel.addTextChangedListener{filteredText->
//            val newItems = marvelCharsItems.filter { items->
//                items.nombre.contains(filteredText.toString())
//            }
//
//            rvAdapter.replaceListItemsAdapter(newItems)
//        }


    }

    fun chargeDataRV(){
        //Marvel List Data
//        lifecycleScope.launch(Dispatchers.IO){
//            val rvAdapter=MarvelAdapter(ListItems().returnMarvelChars()){sendMarvelItem(it)}
//            withContext(Dispatchers.Main){
//                with(binding.rvMarvel){
//                    this.adapter=rvAdapter
//                    this.layoutManager=LinearLayoutManager(
//                        requireActivity(),
//                        LinearLayoutManager.VERTICAL,
//                        false
//                    )
//                }
//            }
//        }

        lifecycleScope.launch(Dispatchers.IO){
            //Para Api Jikan
            val rvAdapter=MarvelAdapter(JikanAnimeLogic().getAllAnimes()){sendMarvelItem(it)}
            //Para Api Marvel
            //val rvAdapter=MarvelAdapter(MarvelLogic().getAllChars("hulk",3)){sendMarvelItem(it)}
            withContext(Dispatchers.Main){
                with(binding.rvMarvel){
                    this.adapter=rvAdapter
                    this.layoutManager=LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }
            }
        }
    }

    fun chargeDataRV2(search: String){
        lifecycleScope.launch(Dispatchers.IO){
            //rvAdapter= MarvelAdapter(JikanAnimeLogic().getAllAnimes()){sendMarvelItem(it)}
            rvAdapter=MarvelAdapter(MarvelLogic().getAllChars(search,10)){sendMarvelItem(it)}
            withContext(Dispatchers.Main){
                with(binding.rvMarvel){
                    this.adapter=rvAdapter
                    this.layoutManager=lmanager
                }
            }
        }
    }

    fun chargeDataRV3(search: String){

        lifecycleScope.launch(Dispatchers.IO){
            var marvelCharsItems= MarvelLogic().getAllChars("spider",5)

            //rvAdapter= MarvelAdapter(JikanAnimeLogic().getAllAnimes()){sendMarvelItem(it)}
            rvAdapter=MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
            withContext(Dispatchers.Main){
                with(binding.rvMarvel){
                    this.adapter=rvAdapter
                    this.layoutManager=lmanager
                }
            }
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


    private fun getDataStore() = requireActivity().dataStore.data.map {prefs->
        UserDataStore(
            name=prefs[stringPreferencesKey("usuario")].orEmpty(),
            sesion=prefs[stringPreferencesKey("sesion")].orEmpty(),
            email = prefs[stringPreferencesKey("email")].orEmpty()
        )

    }

}