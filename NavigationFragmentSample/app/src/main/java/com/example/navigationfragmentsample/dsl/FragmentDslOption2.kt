package com.example.navigationfragmentsample.dsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentDslOption2Binding
import com.example.navigationfragmentsample.dsl.route.AppRoute
import com.example.navigationfragmentsample.dsl.route.navigateWithAnim

class FragmentDslOption2 : Fragment() {
    private lateinit var binding: FragmentDslOption2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dsl_option2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOption2dash1.setOnClickListener {
            findNavController().navigateWithAnim(AppRoute.Option2_1)
        }
    }
}