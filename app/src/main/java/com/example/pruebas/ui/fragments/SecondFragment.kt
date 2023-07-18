package com.example.pruebas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.pruebas.R
import com.example.pruebas.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val nombres = arrayListOf<String>("Sebitas", "Jaime", "Tamarita", "Deivid", "Luci")
        val adapter= ArrayAdapter<String>(
            requireActivity(),
            /*android.R.layout.simple_spinner_item, */
            R.layout.simple_layout,
            nombres)
        binding.spinn.adapter=adapter
        binding.list2.adapter=adapter
    }


}