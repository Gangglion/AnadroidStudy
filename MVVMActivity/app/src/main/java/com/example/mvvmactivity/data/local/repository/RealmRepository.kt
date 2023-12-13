package com.example.mvvmactivity.data.local.repository

import com.example.mvvmactivity.data.local.RealmClient
import com.example.mvvmactivity.ui.recyclerview.model.TempData
import io.realm.Realm
import org.bson.types.ObjectId

class RealmRepository {
    private var realm: Realm = RealmClient.getInstance()
    suspend fun openRealm(){

    }
    suspend fun create(){
        realm.executeTransaction{ r: Realm ->
            val temp = r.createObject(TempData::class.java, ObjectId())
        }
    }
    suspend fun read(){

    }

    suspend fun write(){

    }
    suspend fun delete(){

    }
}