package com.example.mvvmactivity

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MVVMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Realm 초기화
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("temp_realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
            .inMemory()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}