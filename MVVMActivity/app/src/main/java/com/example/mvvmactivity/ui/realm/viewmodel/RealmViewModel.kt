package com.example.mvvmactivity.ui.realm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RealmViewModel(
    private val repository: RealmRepository
) : BaseViewModel() {
    private val _realmData: MutableLiveData<String> = MutableLiveData()
    val realmData: LiveData<String> = _realmData

    fun readData(){
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val deferred = async{
                repository.read()
            }
            val result = deferred.await().toString()
            withContext(Dispatchers.Main){
                _realmData.value = result
            }
        }
    }

    fun goBack(){
        mNavController.navigate(R.id.action_realmFragment_to_recyclerViewFragment)
    }

    fun deleteData(){
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val deferredDelete = async{
                repository.delete()
            }
            deferredDelete.await()
        }
    }
}