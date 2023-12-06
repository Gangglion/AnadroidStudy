package com.example.mvvmactivity.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.test1.viewModel.MainViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(RealmRepository()) as T
    }
}