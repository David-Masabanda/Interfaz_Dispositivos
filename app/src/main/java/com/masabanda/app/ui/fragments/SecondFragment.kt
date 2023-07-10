package com.masabanda.app.ui.fragments

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
import com.google.android.material.snackbar.Snackbar
import com.masabanda.app.R
import com.masabanda.app.databinding.FragmentSecondBinding
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
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
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
        binding= FragmentSecondBinding.inflate(layoutInflater,container,false)
        lmanager =LinearLayoutManager(
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
        startActivity(i)
    }
    private fun chargeDataRV(nombre:String) {

        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getAllCharacters(nombre,5))
            } as MutableList<MarvelChars>
            if(marvelCharacterItems.size==0){
                var f= Snackbar.make(binding.txtBucar, "No se encontro", Snackbar.LENGTH_LONG)
                f.show()
            }
            rvAdapter.items = MarvelLogic().getAllCharacters(nombre ,5)

            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE

        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}