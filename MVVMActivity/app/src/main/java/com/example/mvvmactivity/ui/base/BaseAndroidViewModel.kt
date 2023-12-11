package com.example.mvvmactivity.ui.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    protected lateinit var mNavController: NavController
    protected val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()

        Log.e("glion", "Coroutine Exception : $throwable")
    }

    protected fun setNavController(controller: NavController){
        mNavController = controller
    }
}