package com.example.practice_and.viewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practice_and.R
import com.example.practice_and.databinding.FragmentExViewBindingBinding

class ExViewBindingFragment : Fragment() {

    private lateinit var mBinding: FragmentExViewBindingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentExViewBindingBinding.inflate(inflater) // onCreateView의 LayoutInflater 타입의 매개변수 inflater 이용.

        return mBinding.root
    }
}