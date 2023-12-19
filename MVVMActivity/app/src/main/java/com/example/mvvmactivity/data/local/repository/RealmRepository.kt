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
    suspend fun getAllData(): List<RealmData> = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        val data = realm.where(RealmData::class.java).findAll()
        // MEMO : copyFromRealm 을 사용하면 관리되지 않는 Realm 데이터 사본을 얻어 Realm컨텍스트 외부의 데이터를 사용할 수 있다.
        //  코드 수정 중 "올바르지 않은 스레드에서 Realm 에 접근함" 이라는 에러가 발생한 적이 있는데,
        //  이 메서드를 사용하여 얻어온 데이터가 Realm 객체에서 분리된다.
        val result = realm.copyFromRealm(data)
        realm.close()
        result
    }

    /**
     * Realm Data Write
     */
    suspend fun insertOrUpdateData(data: RealmData) = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            // MEMO : copyToRealmOrUpdate 는 Realm에 객체를 삽입할때 동일한 기본키로 기존 객체를 업데이트가 가능하다.
            //  동일한 기본키가 없다면 새로 삽입한다.
            //  insert로 삽입하게되면 동일한 기본키일 경우 예외를 발생시킨다.
            //  insertOrUpdate 또한 동일한 경과를 나타낸다.
            //  둘다 사용이 가능하나 copyToRealmOrUpdate 는 추가된 객체를 반환한다는 차이점이 있다.

//            val addedData = it.copyToRealmOrUpdate(data) // copyToRealmOrUpdate 는 추가된 객체를 반환한다. addedData 에 저장된다.
            it.insertOrUpdate(data) // 추가된 데이터를 반환하지 않는다.
        }
        realm.close()
    }

    /**
     * Realm Data Delete
     */
    suspend fun deleteAllData() = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val willDeleteData = realm.where(RealmData::class.java).findAll()
            willDeleteData.deleteAllFromRealm()
        }
        realm.close()
    }
}