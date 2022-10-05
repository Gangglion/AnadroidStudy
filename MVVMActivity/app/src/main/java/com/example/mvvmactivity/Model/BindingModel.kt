package com.example.mvvmactivity.Model

class BindingModel {
    private val data : String = "binding 이후 data 호출 성공"

    fun getData() : String{
        return data
    }
}