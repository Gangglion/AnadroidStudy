package com.example.mvvmactivity.data.local.repository

import android.util.Log
import com.example.mvvmactivity.data.local.model.RealmData
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealmRepository {
    /**
     * Realm Data Read
     */
    suspend fun getAllData(): List<RealmData>{
        return withContext(Dispatchers.IO){
            val realm = Realm.getDefaultInstance()
            val data = realm.where(RealmData::class.java).findAll()
            val result = realm.copyFromRealm(data)
            realm.close()
            result
        }
    }
    /**
     * Realm Data Write
     */
    suspend fun insertOrUpdateData(data: RealmData){
        return withContext(Dispatchers.IO) {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction{
                it.copyToRealmOrUpdate(data) // Realm 데이터 객체 RealmData에 적용시킴.
            }
            realm.close()
        }
    }
    /**
     * Realm Data Delete
     */
    suspend fun deleteAllData(){
        return withContext(Dispatchers.IO){
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val willDeleteData = realm.where(RealmData::class.java).findAll()
                willDeleteData.deleteAllFromRealm()
            }
            realm.close()
        }
    }
}