package com.example.mvvmactivity.ui.recyclerview.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmactivity.R
import com.example.mvvmactivity.data.local.repository.RealmRepository
import com.example.mvvmactivity.ui.base.BaseAndroidViewModel
import com.example.mvvmactivity.ui.recyclerview.model.TempData

class RecyclerViewModel(
    private val repository: RealmRepository,
    private val application: Application
) : BaseAndroidViewModel(application) {
    private val _tempList: MutableLiveData<List<TempData>> = MutableLiveData()

    val tempList: LiveData<List<TempData>> = _tempList

    /**
     *  화면 이동 - RealmFragment로
     */
    fun goRealmFragment(){
        mNavController.navigate(R.id.action_recyclerViewFragment_to_realmFragment)
    }


    fun getItemList(){
        val tempArray = application.resources.getStringArray(R.array.temp_list)
        val list: MutableList<TempData> = mutableListOf()
        for(i in tempArray.indices) list.add(TempData(title = tempArray[i], isClick = false))
        _tempList.value = list
    }

    /**
     * 클릭한 아이템일때 flag true로 변경
     */
    fun changeClickFlag(tempData: TempData){
        // MEMO
        //  Adapter 에서 DiffUtil로 리스트를 갱신하기 위해서는 list 자체를 수정해주는 것이 아니라,
        //  deep copy를 한 수정된 리스트를 전달해 주어야 한다.
        //  따라서 _tempList를 업데이트 할때 새로운 리스트를 만들어서, 갱신시켜주어야 diffUtil 에서 변화를 감지할 수 있다.

        val clickIndex = _tempList.value!!.indexOf(tempData)
        val newList = _tempList.value?.mapIndexed { index, item ->
            if(index == clickIndex){
                item.copy(title = item.title, isClick = true)
            } else{
                item.copy(title = item.title, isClick = false)
            }
        }

        _tempList.value = newList?: throw NullPointerException("newList is Null")
        Log.d("shhan", _tempList.value!!.toString())
    }
}