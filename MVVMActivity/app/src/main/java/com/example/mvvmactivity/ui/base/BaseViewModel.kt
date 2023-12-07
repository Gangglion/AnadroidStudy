package com.example.mvvmactivity.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel(){

    protected val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()

        Log.e("glion", "Coroutine Exception : $throwable")
    }
}