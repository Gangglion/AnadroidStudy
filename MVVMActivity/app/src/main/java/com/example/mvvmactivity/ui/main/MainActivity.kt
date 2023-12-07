package com.example.mvvmactivity.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ActivityMainBinding
import com.example.mvvmactivity.di.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // ViewModel에 Repository 와 같은 인자를 전달할때는 ViewModelFactory가 필요함 -> di 폴더 ViewModelFactory 생성
        val viewModelFactory = ViewModelFactory()
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        mBinding.mainViewModel = mainViewModel

        // TODO : 리사이클러 뷰 보이고, 누르면 색 변경, 누르는 항목마다 Realm 에 저장. 하단 버튼 누르면 Fragment 전환되고, Realm 조회한 결과를 TestView 에 뿌려줌
    }
}