package com.example.mvvmactivity.ui.main

import androidx.lifecycle.ViewModel
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel

class MainViewModel(
    private val realmRepository: RealmRepository
) : BaseViewModel() {

}