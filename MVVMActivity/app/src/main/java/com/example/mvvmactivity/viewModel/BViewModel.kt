package com.example.mvvmactivity.viewModel

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.mvvmactivity.model.BModel
import com.example.mvvmactivity.R

class BViewModel {
    private var model : BModel = BModel()
    private val _data = MutableLiveData<String>()
    var data : LiveData<String> = _data // 라이브데이터 사용

    fun initView()
    {
        _data.value=model.clickedButton()
    }
}