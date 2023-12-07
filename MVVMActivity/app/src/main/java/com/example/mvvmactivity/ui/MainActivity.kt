package com.example.mvvmactivity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ActivityMainBinding
import com.example.mvvmactivity.di.ViewModelFactory
import com.example.mvvmactivity.ui.viewmodel.MainViewModel

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

    }
}