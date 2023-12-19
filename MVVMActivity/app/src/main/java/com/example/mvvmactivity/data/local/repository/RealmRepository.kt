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
    suspend fun read(): List<RealmData> = withContext(Dispatchers.IO){
        val realm = Realm.getDefaultInstance()
        val result = realm?.where(RealmData::class.java)!!.findAll()
        return@withContext result
    }
    /**
     * Realm Data Write
     */
    suspend fun write(realmData: RealmData) = withContext(Dispatchers.IO){
        val realm = Realm.getDefaultInstance()
        try{
            realm?.executeTransaction {
                it.insertOrUpdate(realmData)
            }
        } catch(e: Exception){
            e.printStackTrace()
        } finally{
            realm?.close()
        }
    }
    /**
     * Realm Data Delete
     */
    suspend fun delete() = withContext(Dispatchers.IO){
        val realm = Realm.getDefaultInstance()
        realm?.executeTransaction {
            it.deleteAll()
        }
    }
}