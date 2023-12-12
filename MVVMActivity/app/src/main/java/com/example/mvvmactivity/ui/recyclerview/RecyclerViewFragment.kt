package com.example.mvvmactivity.ui.recyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.databinding.FragmentRecyclerViewBinding
import com.example.mvvmactivity.di.ViewModelFactory
import com.example.mvvmactivity.ui.recyclerview.adapter.OnItemClickListener
import com.example.mvvmactivity.ui.recyclerview.adapter.TempAdapter
import com.example.mvvmactivity.ui.recyclerview.model.TempData
import com.example.mvvmactivity.ui.recyclerview.viewmodel.RecyclerViewModel

class RecyclerViewFragment : Fragment() {
    private lateinit var mBinding: FragmentRecyclerViewBinding
    private lateinit var mViewModel: RecyclerViewModel
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewModel에 Repository 와 같은 인자를 전달할때는 ViewModelFactory가 필요함 -> di 폴더 ViewModelFactory 생성
        val viewModelFactory = ViewModelFactory(RealmRepository(), requireActivity().application)
        mViewModel = ViewModelProvider(this, viewModelFactory)[RecyclerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false)
        mBinding.lifecycleOwner = this.viewLifecycleOwner
        mBinding.recyclerViewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setNavController(Navigation.findNavController(view))
        mViewModel.getItemList()
        val adapter = TempAdapter(mViewModel.tempList.value!!, object : OnItemClickListener{
            override fun onClick(view: View, tempData: TempData) {
                mViewModel.changeClickFlag(tempData)
                // TODO : tempData 의 title Realm 에 저장
            }
        })
        mBinding.rcList.apply{
            layoutManager = LinearLayoutManager(mContext)
            hasFixedSize()
            this.adapter = adapter
            itemAnimator = null // 리스트 변화 시 애니매이션 효과 제거
        }
        mViewModel.tempList.observe(viewLifecycleOwner){ items ->
            adapter.updateData(items)
        }
    }
}