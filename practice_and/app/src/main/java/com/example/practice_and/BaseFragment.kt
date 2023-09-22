package com.example.practice_and

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment(fragment: Fragment) : Fragment() {

    private val backPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            (activity as MainActivity).exitFragment(fragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onDetach() {
        backPressedCallback.remove()
        super.onDetach()
    }
}