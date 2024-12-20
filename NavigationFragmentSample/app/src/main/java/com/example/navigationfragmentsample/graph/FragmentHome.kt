package com.example.navigationfragmentsample.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnOption1.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentOption1)
            }
            btnOption2.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentOption2)
            }
        }
    }
}