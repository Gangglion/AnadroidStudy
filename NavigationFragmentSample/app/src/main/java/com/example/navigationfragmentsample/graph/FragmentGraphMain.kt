package com.example.navigationfragmentsample.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentGraphMainBinding

class FragmentGraphMain : Fragment() {
    private lateinit var binding: FragmentGraphMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_main, container, false)
        return binding.root
    }
}