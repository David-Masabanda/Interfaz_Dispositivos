package com.example.pruebaxd.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.pruebaxd.R
import com.example.pruebaxd.data.entities.marvel.MarvelChars
import com.example.pruebaxd.databinding.FragmentFirstBinding
import com.example.pruebaxd.ui.activities.DetailsMarvelItem
import com.example.pruebaxd.ui.adapters.MarvelAdapter
import com.example.pruebaxd.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {


    private lateinit var binding: FragmentFirstBinding

    private lateinit var lmanager: LinearLayoutManager

    //private lateinit var gmanager: GridLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private var marvelItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    //El limite nunca cambia
    private val limit=99
    private var offset=0

    private var rvAdapter: MarvelAdapter = MarvelAdapter ({ sendMarvelItems(it) },{saveMarvelItem(it)})
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(
            layoutInflater,
            container,
            false
        )
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
//        gmanager = GridLayoutManager(
//            requireActivity(), 2
//        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>("Spiderman", "Invisible Woman", "Eternity", "Black Widow")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )
        binding.spinner.adapter = adapter
        chargeDataRVInit(offset,limit)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV(offset,limit)
            binding.rvSwipe.isRefreshing = false
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        if ((v + p) >= t) {
                            var newItems = listOf<MarvelChars>()
                            if (offset < 99) {
                                //Log.i("En el scrollview Offset if","$offset")
                                updateDataRV(limit, offset)
                                lifecycleScope.launch((Dispatchers.Main)) {
                                    this@FirstFragment.offset += limit
                                    newItems = withContext(Dispatchers.IO) {
                                        return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))

                                    }
                                    rvAdapter.updateListItems(newItems)

                                }
                            } else {
                                //Log.i("En el scrollview Offset else","$offset")
                                if (offset == 99) {
                                    updateDataRV(limit, offset)
                                } else {
                                    updateAdapterRV()
                                    lifecycleScope.launch((Dispatchers.Main)) {
                                        rvAdapter.updateListItems(listOf())

                                    }
                                }


                            }

                        }
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                marvelItemsDB=MarvelLogic().getAllCharactersDB().toMutableList()
            }

        }
    }

    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        i.putExtra("comic", item)
        i.putExtra("image", item)
        startActivity(i);
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {
        return if(item==null || marvelItemsDB.contains(item)){
            false
        }else{
            lifecycleScope.launch(Dispatchers.Main){
                withContext(Dispatchers.IO){
                    MarvelLogic().insertMarvelCharstoDB(listOf(item))
                    marvelItemsDB=MarvelLogic().getAllCharactersDB().toMutableList()
                }

            }
            true
        }
    }


    fun corrotine() {
        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Fer"
            name = withContext((Dispatchers.IO)) {
                name = "Jairo"
                return@withContext name
            }
        }
    }

    fun chargeDataRV(offset:Int,limit:Int) {

        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (
                        MarvelLogic().getAllMarvelChars(offset, limit))

            } as MutableList<MarvelChars>

            rvAdapter.items =marvelCharacterItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager =lmanager
            }
        }
    }

    fun updateDataRV(limit: Int, offset: Int) {
        var items: List<MarvelChars> = listOf()
        lifecycleScope.launch(Dispatchers.Main) {
            items = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
            }

            rvAdapter.updateListItems(items)
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }


    }
    fun updateAdapterRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }
    }

    private fun chargeDataRVDB() {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                var marvelCharacterItems = MarvelLogic().getAllCharactersDB().toMutableList()

                if (marvelCharacterItems.isEmpty()) {

                    marvelCharacterItems =
                        (MarvelLogic().getAllMarvelChars(offset, limit).toMutableList())
                    MarvelLogic().insertMarvelCharstoDB(marvelCharacterItems)
                }
                return@withContext marvelCharacterItems
            }

            rvAdapter.items = marvelCharacterItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }


    }
    private fun chargeDataRVInit( offset:Int,limit:Int) {
        if(Metodos().isOnline(requireActivity())){
            //Mucho ojo con las corrutinas y hasta donde colococamos los {}
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext MarvelLogic().getAllMarvelChars(offset, limit)
                } as MutableList<MarvelChars>

                rvAdapter.items = marvelCharacterItems

                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }

                this@FirstFragment.offset+=limit
            }
        }else{
            Snackbar.make(
                binding.cardView,
                "Nohay conexion",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }



//    fun chargeDataRVAPI(limit:Int, offset:Int){
//
//        lifecycleScope.launch(Dispatchers.Main) {
//            marvelCharacterItems = withContext(Dispatchers.IO) {
//                return@withContext MarvelLogic().getAllMarvelChars(offset,limit))
//            }
//            rvAdapter.items = marvelCharacterItems
//            binding.rvMarvelChars.apply {
//                this.adapter = rvAdapter
//                this.layoutManager = lmanager
//                gmanager.scrollToPositionWithOffset(pos,10)
//            }
//            this@FirstFragment.offset=offset+limit
//
//        }
//    }

}