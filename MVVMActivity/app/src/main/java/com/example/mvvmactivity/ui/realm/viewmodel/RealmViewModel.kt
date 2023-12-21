package com.example.mvvmactivity.ui.realm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.model.RealmData
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseViewModel
import com.example.mvvmactivity.ui.realm.model.TextViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RealmViewModel(
    private val repository: RealmRepository
) : BaseViewModel() {
    private val _dataList: MutableLiveData<String> = MutableLiveData()
    val dataList: LiveData<String> = _dataList
    // 양방향 데이터 바인딩 - EditText
    // MEMO : LiveData 형식은 양방향 데이터 바인딩이 가능하지 않음. 왜? 변경이 되지 않기 때문.
    //  기존에 _value, value 형식은 viewModel 내부에서 _value 를 변경하고, viewModel 외부에서 관측하기 위함이였지만,
    //  양방향 데이터 바인딩은 layout과 viewModel 간 데이터가 양방향으로 변경되기 때문에 변경이 불가능한 LiveData 형식으로는 불가능하다.
    val inputData: MutableLiveData<String> = MutableLiveData("")

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

    fun inputData(){
        if(inputData.value!!.isNotEmpty()){
            val insertData = RealmData(index = 28, title = inputData.value)
            viewModelScope.launch(exceptionHandler) {
                val deferred = async{
                    repository.insertOrUpdateData(insertData)
                }
                deferred.await()
                getAllData()
                inputData.value = ""
            }
        }
    }
}