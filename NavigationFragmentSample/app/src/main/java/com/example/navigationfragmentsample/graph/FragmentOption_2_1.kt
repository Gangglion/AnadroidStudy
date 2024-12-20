package com.example.navigationfragmentsample.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.graph.data.ResultData
import com.example.navigationfragmentsample.databinding.FragmentOption21Binding
import kotlin.random.Random

class FragmentOption_2_1 : Fragment() {
    private lateinit var binding: FragmentOption21Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_option_2_1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnResult.setOnClickListener {
            val resultData = ResultData("Option 2-1 에서 옴", Random.nextInt(1, 100))
            val action = FragmentOption_2_1Directions.actionFragmentOption21ToFragmentResult(resultData)
            findNavController().navigate(action)
        }
    }
}