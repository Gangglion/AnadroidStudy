package com.example.mvvmactivity.ui.realm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RealmViewModel(
    private val repository: RealmRepository
) : BaseViewModel() {
    private val _realmData: MutableLiveData<String> = MutableLiveData()
    val realmData: LiveData<String> = _realmData
    init{
        viewModelScope.launch(exceptionHandler) {
            val deferred = async{
                repository.read()
            }
            _realmData.value = deferred.await().toString()
        }
    }

    fun goBack(){
        mNavController.navigate(R.id.action_realmFragment_to_recyclerViewFragment)
    }

    fun deleteData(){
        viewModelScope.launch(exceptionHandler) {
            val deferredDelete = async{
                repository.delete()
            }
            deferredDelete.await()
            val deferredRead = async{
                repository.read()
            }
            _realmData.value = deferredRead.await().toString()
        }
    }
}