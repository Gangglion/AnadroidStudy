package com.example.practice_and.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.R
import com.example.practice_and.activity.MainActivity
import com.example.practice_and.adapter.RcBlogAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RcBlogFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mContext: Context

    private val backPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            (activity as MainActivity).exitFragment(this@RcBlogFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tempData = ArrayList<String>().apply{
            this.add("18574623")
            this.add("18574624")
            this.add("18574625")
            this.add("18574626")
            this.add("18574630")
            this.add("18574631")
            this.add("18574633")
            this.add("18574643")
            this.add("18574646")
            this.add("18574650")
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rc_blog, container, false)
        val rcView =  view.findViewById<RecyclerView>(R.id.rc_ex)
        rcView.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rcView.adapter = RcBlogAdapter(tempData, mContext)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RcBlogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDetach() {
        backPressedCallback.remove()
        super.onDetach()
    }
}