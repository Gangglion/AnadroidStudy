package com.glion.testgather.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glion.testgather.R
import com.glion.testgather.adapter.TempRecyclerViewAdapter
import com.glion.testgather.databinding.ActivityMainBinding
import com.glion.testgather.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment() {
    private var mBinding: FragmentRecyclerViewBinding? = null
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        val viewGroup = mBinding!!.root

        // 리사이클러뷰 아이템 준비
        val itemList: ArrayList<Int> = ArrayList()
        for(i in 0 until 100){
            itemList.add(i)
        }
        mBinding!!.rvTemp.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        mBinding!!.rvTemp.adapter = TempRecyclerViewAdapter(itemList, mContext)
        return viewGroup
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Fragment는 view보다 오래 지속되어 Fragment의 Lifecycle로 인해 메모리 누수 발생 가능함
        // 따라서 Fragment 가 destroy될때 바인딩을 해제시켜준다
        mBinding = null
    }
}