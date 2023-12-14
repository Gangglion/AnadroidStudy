package com.example.mvvmactivity.data.local.repository

import com.example.mvvmactivity.data.local.model.RealmData
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealmRepository {
    /**
     * Realm Data Read
     */
    // MEMO : Realm 작업은 Realm 을 생성한 스레드에서만 가능하다. 따라서 withContext를 통해 코루틴이 Main스레드를 사용하도록 한다.
    suspend fun read(): List<RealmData> = withContext(Dispatchers.Main){
        val realm = Realm.getDefaultInstance()
        val result = realm?.where(RealmData::class.java)!!.findAll()
        return@withContext result
    }
    /**
     * Realm Data Write
     */
    suspend fun write(realmData: RealmData) = withContext(Dispatchers.Main){
        val realm = Realm.getDefaultInstance()
        realm?.executeTransaction {
            it.insertOrUpdate(realmData)
        }
    }
    /**
     * Realm Data Delete
     */
    suspend fun delete() = withContext(Dispatchers.Main){
        val realm = Realm.getDefaultInstance()
        realm?.executeTransaction {
            it.deleteAll()
        }
    }
}