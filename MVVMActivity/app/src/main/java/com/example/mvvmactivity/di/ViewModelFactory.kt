package com.example.mvvmactivity.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import com.example.mvvmactivity.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: RealmRepository? = null, private val application: Application? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(BaseViewModel::class.java) ->{
                @Suppress("UNCHECKED_CAST")
                MainViewModel(repository!!) as T
            }
            else ->{
                throw IllegalArgumentException("Unknown ViewModel Arguments")
            }
        }
/*        @Suppress("UNCHECKED_CAST")
        return MainViewModel(RealmRepository()) as T*/
    }
}