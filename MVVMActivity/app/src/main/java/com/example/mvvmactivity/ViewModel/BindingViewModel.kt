package com.example.mvvmactivity.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.mvvmactivity.Model.BindingModel

class BindingViewModel() {
    private val bindingModel = BindingModel()
    private val _data = MutableLiveData<String>()
    val data : LiveData<String> = _data

    fun getData(){
        _data.value = bindingModel.getData()
    }
}