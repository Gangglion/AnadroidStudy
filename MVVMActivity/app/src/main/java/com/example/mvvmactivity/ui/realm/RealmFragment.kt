package com.example.mvvmactivity.ui.realm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.databinding.FragmentRealmBinding
import com.example.mvvmactivity.di.ViewModelFactory
import com.example.mvvmactivity.ui.realm.viewmodel.RealmViewModel

class RealmFragment : Fragment() {
    private lateinit var mBinding: FragmentRealmBinding
    private lateinit var mViewModel: RealmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory(RealmRepository())
        mViewModel = ViewModelProvider(this, factory)[RealmViewModel::class.java]
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_realm, container, false)
        mBinding.realmViewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mViewModel.getAllData()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.setNavController(Navigation.findNavController(mBinding.root))

        // 에러 처리
        mBinding.inputEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(mBinding.inputEt.text!!.isEmpty()){
                    mBinding.inputLayout.error = "Title을 입력하세요"
                } else{
                    mBinding.inputLayout.error = null
                }
            }
        })
    }
}