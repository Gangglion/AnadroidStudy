package com.example.mvvmactivity.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmactivity.R
import com.example.mvvmactivity.ViewModel.NoBindingViewModel

class NoBindingMainActivity : AppCompatActivity() {
    private lateinit var noBindingViewModel : NoBindingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nobindingmain)

        // ViewModel에 위임(코틀린에는 new 연산자없음)
        noBindingViewModel = NoBindingViewModel(this);
    }
}