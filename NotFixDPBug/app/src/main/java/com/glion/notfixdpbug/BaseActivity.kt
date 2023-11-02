package com.glion.notfixdpbug

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity : AppCompatActivity() {
    val TAG = "shhan"

    // Test - 기존 사용되던 코드, deprecated 되있음
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val configuration: Configuration = resources.configuration
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        configuration.densityDpi = resources.displayMetrics.xdpi.toInt()
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    // MEMO : deprecated 된 부분 수정할 수 있는 방법
/*    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(FixConfiguration.wrap(newBase!!))
    }*/
}