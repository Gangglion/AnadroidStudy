package com.example.navigationfragmentsample.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentOption2Binding

class FragmentOption2 : Fragment() {
    private lateinit var binding: FragmentOption2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_option2, container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOption2dash1.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentOption2_to_fragmentOption_2_1)
        }
    }
}