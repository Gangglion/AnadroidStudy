package com.example.navigationfragmentsample.dsl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.dsl.data.DslResultData
import com.example.navigationfragmentsample.databinding.FragmentDslResultBinding
import com.example.navigationfragmentsample.dsl.route.AppRoute
import com.example.navigationfragmentsample.dsl.route.navigateWithAnim

class FragmentDslResult : Fragment() {
    private lateinit var binding: FragmentDslResultBinding
    private val args: DslResultData by lazy {
        findNavController().getBackStackEntry<AppRoute.Result>().toRoute<AppRoute.Result>().resultData
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dsl_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = args.title
        binding.tvValue.text = args.value.toString()
        binding.btnFind.setOnClickListener {
            findNavController().navigateWithAnim(AppRoute.Find)
        }
        binding.btnHome.setOnClickListener {
            findNavController().navigateWithAnim(AppRoute.Home, true)
        }
    }
}