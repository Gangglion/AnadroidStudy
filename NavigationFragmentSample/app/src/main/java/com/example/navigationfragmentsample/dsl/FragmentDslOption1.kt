package com.example.navigationfragmentsample.dsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.dsl.data.DslResultData
import com.example.navigationfragmentsample.databinding.FragmentDslOption1Binding
import com.example.navigationfragmentsample.dsl.route.AppRoute
import com.example.navigationfragmentsample.dsl.route.navigateWithAnim
import kotlin.random.Random

class FragmentDslOption1 : Fragment() {
    lateinit var binding: FragmentDslOption1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dsl_option1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnResult.setOnClickListener {
            val resultData = DslResultData("Option1 에서 옴", Random.nextInt(1, 100))
            findNavController().navigateWithAnim(AppRoute.Result(resultData))
        }
    }
}