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

//        chargeDataRV3("Spider")

        chargeDataRVInit(limit,offset)

        binding.rvSwipe.setOnRefreshListener {
//            chargeDataRV()
//            chargeDataRV3("Spider")

            chargeDataRVAPI(offset = offset,limit = limit)
            binding.rvSwipe.isRefreshing=false
            gmanager.scrollToPositionWithOffset(5, 20)
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
                            //val newItems=MarvelLogic().getAllChars("Hulk",10)
                            val newItems=MarvelLogic().getAllMarvelChars(offset,limit)
                            withContext(Dispatchers.Main){
                                rvAdapter.updateListItems(newItems)
                                this@FirstFragment.offset+=offset
                            }
                        }
                    }

                    //Version 2
//                    if ((v+p)>=t){
//                        lifecycleScope.launch(Dispatchers.Main){
//                            val newItems= with(Dispatchers.IO){
//                                MarvelLogic().getAllMarvelChars(offset,limit)
//                            }
//                            rvAdapter.updateListItems(newItems)
//                            this@FirstFragment.offset+=offset
//                        }
//                    }
                }
            }
        })

        //Aqui puedo hacer un filtro despues, necesito un Text.... en le layout
        //Debo importar el correcto textchanged { } crossinline Editable
        //no es rvMarvel sino el txtFilter o como lo tenga

//
//        binding.rvMarvel.addTextChangedListener{filteredText->
//            val newItems = marvelCharsItems.filter { items->
//                items.nombre.lowercase().contains(filteredText.toString().lowercase())
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
            rvAdapter=MarvelAdapter(MarvelLogic().getMarvelChars(search,10)){sendMarvelItem(it)}
            withContext(Dispatchers.Main){
                with(binding.rvMarvel){
                    this.adapter=rvAdapter
                    this.layoutManager=lmanager
                }
            }
        }
    }

    fun chargeDataRV3(search: String){

        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO){
                return@withContext MarvelLogic().getAllMarvelChars(0,99)
            }

            //rvAdapter= MarvelAdapter(JikanAnimeLogic().getAllAnimes()){sendMarvelItem(it)}
            rvAdapter=MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
            binding.rvMarvel.apply {
                this.adapter=rvAdapter
                this.layoutManager=lmanager
//                this.layoutManager=gmanager

            }
        }
    }


    fun chargeDataRVInit(limit: Int, offset: Int){

        if (Metodos().isOnline(requireActivity())){
            lifecycleScope.launch(Dispatchers.Main){
                marvelCharsItems = withContext(Dispatchers.IO){
                    return@withContext MarvelLogic().getInitChars(limit,offset)
                }

                //rvAdapter= MarvelAdapter(JikanAnimeLogic().getAllAnimes()){sendMarvelItem(it)}
                rvAdapter=MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
                binding.rvMarvel.apply {
                    this.adapter=rvAdapter
                    this.layoutManager=lmanager
//                    this.layoutManager=gmanager
                }
                this@FirstFragment.offset+=limit
            }
        }else{
            Snackbar.make(
                binding.rvMarvel,"No hay conexion", Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun chargeDataRVAPI(limit: Int, offset: Int){
        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO){
                return@withContext MarvelLogic().getAllMarvelChars(offset,limit)
            }

            rvAdapter = MarvelAdapter(marvelCharsItems){sendMarvelItem(it)}
            binding.rvMarvel.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            this@FirstFragment.offset = offset+limit
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