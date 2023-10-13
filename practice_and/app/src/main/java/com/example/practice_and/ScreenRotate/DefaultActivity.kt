package com.example.practice_and.ScreenRotate

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.example.practice_and.R

class DefaultActivity : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_default)
        Log.d("shhan", "Portrait Density on Create : ${resources.configuration.densityDpi}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("shhan", "Portrait Density on Start : ${resources.configuration.densityDpi}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("shhan", "Portrait Density on Resume : ${resources.configuration.densityDpi}")
    }
}