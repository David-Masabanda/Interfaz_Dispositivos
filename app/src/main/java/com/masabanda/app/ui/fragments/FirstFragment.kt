package com.masabanda.app.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masabanda.app.databinding.FragmentFirstBinding
import com.masabanda.app.logic.data.MarvelChars
import com.masabanda.app.logic.marvelLogic.MarvelLogic
import com.masabanda.app.ui.activities.DetailsMarvelItem
import com.masabanda.app.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gmanager: GridLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private  var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItems(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,
            false
        )
        lmanager =LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        gmanager= GridLayoutManager(
            requireActivity(),2
        )
        return binding.root
    }


    override fun onStart() {

        super.onStart()
        val names = arrayListOf<String>("Carlos", "Juan", "Xavier", "Andres", "Pepe", "Antonio")

        chargeDataRV()
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }
        binding.rvMarvel.addOnScrollListener(
            object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(dy>0){
                        val v= lmanager.childCount
                        val p= lmanager.findFirstVisibleItemPosition()
                        val t= lmanager.itemCount

                        if((v+p)>=t){
                            lifecycleScope.launch((Dispatchers.IO)){
                                val items= MarvelLogic().getAllMarvelChars(0,99)
                                /* val newItems = MarvelLogic().getAllCharacters(
                                     name="cap" ,
                                     5)*/
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(items)
                                }

                            }
                        }
                    }

                }
            }
        )
        binding.txtfilter.addTextChangedListener { filteredText->
            val newItems= marvelCharacterItems.filter {
                items->items.name.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }

    }

    fun sendMarvelItems(item: MarvelChars) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }


    private fun chargeDataRV() {


        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getAllMarvelChars (0,99))
            } as MutableList<MarvelChars>

            rvAdapter.items =
                    //JikanAnimeLogic().getAllAnimes()
                MarvelLogic().getAllMarvelChars(0 ,99)

            //ListItems().returnMarvelChar()
            /*   JikanAnimeLogic().getAllAnimes()
   ) { sendMarvelItems(it) }

*/

            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = gmanager
            }

        }
    }



    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


