package com.glion.notfixdpbug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton

class BFragment : Fragment() {
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        Log.d((activity as MainActivity).TAG, "BFragment configuration : ${resources.configuration.densityDpi}")

        view.findViewById<AppCompatButton>(R.id.btn_movie).apply{
            setOnClickListener {
                startActivity(Intent(mContext, VideoActivity::class.java))
            }
        }
        return view
    }
}