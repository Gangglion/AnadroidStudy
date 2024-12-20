package com.example.navigationfragmentsample.graph

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.databinding.FragmentGraphResultBinding
import com.example.navigationfragmentsample.graph.data.ResultData

class FragmentGraphResult : Fragment() {
    private lateinit var binding: FragmentGraphResultBinding
    private var mGetTitle: String = ""
    private var mGetValue: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val data = it.getParcelable<ResultData>("ResultData")
            Log.d("glion", data.toString())
            mGetTitle = data?.title ?: ""
            mGetValue = data?.value ?: 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph_result, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvTitle.text = mGetTitle
            tvValue.text = mGetValue.toString()
            btnFind.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentResult_to_fragmentFind)
            }
            btnHome.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentResult_to_fragmentHome)
            }
        }
    }
}