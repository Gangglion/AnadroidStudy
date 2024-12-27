package com.example.navigationfragmentsample.deeplink

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentDeeplinkBinding

class DeeplinkFragment : Fragment() {
    private lateinit var binding: FragmentDeeplinkBinding
    private var mTitle: String = ""
    private var mContents: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = arguments?.getString("title") ?: "null"
        mContents = arguments?.getString("contents") ?: "null"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deeplink, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = mTitle
        binding.tvContents.text = mContents
    }
}