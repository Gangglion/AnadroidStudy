package com.example.mvvmactivity.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmactivity.R
import com.example.mvvmactivity.viewModel.ViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewModel에 위임(코틀린에는 new 연산자없음)
        viewModel = ViewModel(this);
    }
}