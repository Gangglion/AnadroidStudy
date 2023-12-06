package com.example.mvvmactivity.ui.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ActivityMainBinding
import com.example.mvvmactivity.di.ViewModelFactory
import com.example.mvvmactivity.ui.test1.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // ViewModel에 위임(코틀린에는 new 연산자없음)
        val viewModelFactory = ViewModelFactory()
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        mBinding.mainViewModel = mainViewModel

    }
}