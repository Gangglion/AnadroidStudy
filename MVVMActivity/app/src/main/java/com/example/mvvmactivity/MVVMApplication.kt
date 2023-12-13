package com.example.mvvmactivity

import android.app.Application
import io.realm.Realm

class MVVMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Realm 초기화
        Realm.init(this)
    }
}