package com.example.pruebaxd.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pruebaxd.R
import com.example.pruebaxd.databinding.FragmentSecondBinding
import com.example.pruebaxd.logic.validator.ListItems
import com.example.pruebaxd.ui.adapters.MarvelAdapter


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(
            layoutInflater, container, false
        )

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

       // binding.spinn.adapter=adapter
        //binding.list2.adapter=adapter




        val rvAdapter=MarvelAdapter(ListItems().returnMarvelChars())
        val rvMarvel=binding.rvMarvel

        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager=LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false)


    }


}