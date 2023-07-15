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

    private lateinit var gmanager: GridLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    //El limite nunca cambia
    private val limit=99
    private var offset=0

    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItems(it) }
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
        gmanager = GridLayoutManager(
            requireActivity(), 2
        )
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
            chargeDataRVInit(offset,limit)
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
                            lifecycleScope.launch((Dispatchers.IO)) {
                                val newItems = MarvelLogic().getAllMarvelChars(offset, limit)
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(newItems)
                                }
                            }
                        }
                    }
                }
            })
    }

    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i);
    }

    fun chargeDataRV() {

        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (
                        MarvelLogic().getAllMarvelChars(0, 99))
//                        JikanAnimeLogic().getAllAnimes ())
            } as MutableList<MarvelChars>

            rvAdapter.items =
                MarvelLogic().getAllMarvelChars(0, 99)
//                JikanAnimeLogic().getAllAnimes()
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gmanager
            }
        }
    }
    private fun chargeDataRVInit( offset:Int,limit:Int,) {
        if(Metodos().isOnline(requireActivity())){
            //Mucho ojo con las corrutinas y hasta donde colococamos los {}
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext MarvelLogic().getInitChars(offset, limit)
                }
                this@FirstFragment.offset+=limit
                rvAdapter.items = marvelCharacterItems
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gmanager
                }
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