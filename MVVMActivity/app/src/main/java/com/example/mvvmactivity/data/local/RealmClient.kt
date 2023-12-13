package com.example.mvvmactivity.data.local

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmFileException
import java.lang.RuntimeException

object RealmClient {
    private val config = RealmConfiguration.Builder()
        .name("temp_realm")
        .allowQueriesOnUiThread(true)
        .allowWritesOnUiThread(true)
        .compactOnLaunch()
        .inMemory()
        .build()

    private val realm = Realm.getInstance(config)

    fun getInstance(): Realm{
        return try{
            realm
        } catch(e: RealmFileException){
            throw RuntimeException("RealmFileException : ${e.kind}")
        }
    }
}