package com.glion.bugreenactment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity : AppCompatActivity() {
    val TAG = "shhan"

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(FixConfiguration.wrap(newBase!!))
    }
}