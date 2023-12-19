package com.example.mvvmactivity.ui.realm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import com.example.mvvmactivity.ui.realm.model.TextViewData
import kotlinx.coroutines.launch

class RealmViewModel(
    private val repository: RealmRepository
) : BaseViewModel() {
    private val _dataList: MutableLiveData<String> = MutableLiveData()
    val dataList: LiveData<String> = _dataList

    fun getAllData(){
        viewModelScope.launch{
            // RealmObject 객체로 이루어진 List 는 일반 리스트와 다름. Realm DB에서 사용하는 객체임
            // 따라서 toString으로 TextView에 출력시키고자 할땐, 임시 list로 넣은 다음에 이를 출력해주어야 함.
            val list: List<TextViewData> = repository.getAllData().map{
                TextViewData(index = it.index ?: -1, title = it.title ?: "")
            }
            _dataList.value = list.toString()
        }
    }


    fun goBack(){
        mNavController.navigate(R.id.action_realmFragment_to_recyclerViewFragment)
    }

    fun deleteAllData(){
        viewModelScope.launch{
            repository.deleteAllData()
            val list = repository.getAllData()
            _dataList.value = list.toString()
        }
    }
}