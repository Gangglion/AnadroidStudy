package com.example.practice_and.bluetooth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practice_and.R
import com.example.practice_and.databinding.FragmentBleBinding

class BleFragment : Fragment() {
    private lateinit var mBinding: FragmentBleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBleBinding.inflate(inflater)

        return mBinding.root
    }
}