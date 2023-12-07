package com.example.mvvmactivity.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import com.example.mvvmactivity.ui.viewmodel.MainViewModel
import com.example.mvvmactivity.ui.viewmodel.ViewModelWithApplication
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: RealmRepository? = null, private val application: Application? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(BaseViewModel::class.java) ->{
                @Suppress("UNCHECKED_CAST")
                MainViewModel(repository!!) as T
            }
            modelClass.isAssignableFrom(AndroidViewModel::class.java) ->{
                @Suppress("UNCHECKED_CAST")
                ViewModelWithApplication(application!!) as T
            }
            else ->{
                throw IllegalArgumentException("Unknown ViewModel Arguments")
            }
        }
/*        @Suppress("UNCHECKED_CAST")
        return MainViewModel(RealmRepository()) as T*/
    }
}