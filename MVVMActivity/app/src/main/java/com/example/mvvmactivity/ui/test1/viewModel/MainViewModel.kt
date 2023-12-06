package com.example.mvvmactivity.ui.test1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmactivity.data.local.repository.RealmRepository

class MainViewModel(
    private val realmRepository: RealmRepository
) : ViewModel() {

}