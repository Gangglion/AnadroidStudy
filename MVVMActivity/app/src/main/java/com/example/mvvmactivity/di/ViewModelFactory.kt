package com.example.mvvmactivity.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.realm.viewmodel.RealmViewModel
import com.example.mvvmactivity.ui.recyclerview.viewmodel.RecyclerViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: RealmRepository? = null,
    private val application: Application? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // MEMO : isAssignableFrom 은 실제 존재하는 viewModel 클래스를 기반으로 검사해야 한다.
            modelClass.isAssignableFrom(RealmViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                RealmViewModel(repository!!) as T
            }

            modelClass.isAssignableFrom(RecyclerViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                RecyclerViewModel(repository!!, application!!) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel Arguments")
            }
        }
    }
}