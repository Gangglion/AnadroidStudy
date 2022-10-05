package com.example.mvvmactivity.View

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmactivity.R
import com.example.mvvmactivity.ViewModel.BindingViewModel
import com.example.mvvmactivity.databinding.ActivityBindingmainBinding

class BindingMainActivity : AppCompatActivity() {
    private lateinit var bindingViewModel : BindingViewModel
    private lateinit var binding : ActivityBindingmainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bindingmain)
        setupObserver()
        setupClickListener()
    }
    private fun setupObserver(){
        bindingViewModel.data.observe(this) {
            binding.textView.text = it;
        }
    }
    private fun setupClickListener(){
        binding.textBtn.setOnClickListener{
            bindingViewModel.getData()
        }
    }
}