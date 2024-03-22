package com.glion.scheduletest

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class Applilcation : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}