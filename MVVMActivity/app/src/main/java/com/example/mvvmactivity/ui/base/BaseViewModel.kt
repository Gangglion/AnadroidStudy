package com.example.mvvmactivity.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel(){
    protected lateinit var mNavController: NavController
    protected val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()

        Log.e("glion", "Coroutine Exception : $throwable")
    }

    fun setNavController(controller: NavController){
        mNavController = controller
    }
}