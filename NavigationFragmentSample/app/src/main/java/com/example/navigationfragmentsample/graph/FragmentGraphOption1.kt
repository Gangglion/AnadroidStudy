package com.example.navigationfragmentsample.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentGraphOption1Binding
import com.example.navigationfragmentsample.graph.data.ResultData
import kotlin.random.Random

class FragmentGraphOption1 : Fragment() {
    lateinit var binding: FragmentGraphOption1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_option1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnResult.setOnClickListener {
            val resultData = ResultData("Option1 에서 옴", Random.nextInt(1, 100))
            val action = FragmentGraphOption1Directions.actionFragmentOption1ToFragmentResult(resultData)
            findNavController().navigate(action)
        }
    }
}