package com.example.mvvmactivity.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ActivityBmainBinding
import com.example.mvvmactivity.viewModel.BViewModel

class BMainActivity : AppCompatActivity() {
    private var viewModel : BViewModel = BViewModel()
    private lateinit var binding : ActivityBmainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bmain)
        binding.viewmodel = viewModel
        // binding.textBtn.setOnClickListener { viewModel.initView() }
        setupObserver()
    }
    private fun setupObserver(){ // 라이브데이터 변경 확인 후 변경내용 적용
        viewModel.data.observe(this) {
            binding.textView.text = it
        }
    }
}