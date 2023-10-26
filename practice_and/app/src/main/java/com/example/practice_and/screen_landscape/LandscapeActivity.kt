package com.example.practice_and.screen_landscape

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.widget.AppCompatTextView
import com.example.practice_and.App
import com.example.practice_and.R

class LandscapeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landscape)

        setTheme(R.style.FullScreenTheme)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars())
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        findViewById<AppCompatTextView>(R.id.tv_landscape).apply{
            setOnClickListener {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                text = "크기 변화 확인"
            }
        }

        Log.v(App.TAG, "LandscapeActivity - onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.v(App.TAG, "LandscapeActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(App.TAG, "LandscapeActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(App.TAG, "LandscapeActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(App.TAG, "LandscapeActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(App.TAG, "LandscapeActivity - onDestroy")
    }
}