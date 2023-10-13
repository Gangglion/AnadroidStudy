package com.example.practice_and.ScreenRotate

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.example.practice_and.R

class LandscapeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_landscape)
        Log.d("shhan", "Landscape Density on Create : ${resources.configuration.densityDpi}")

        findViewById<AppCompatButton>(R.id.btn_back).apply{
            setOnClickListener {
                startActivity(Intent(context, DefaultActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("shhan", "Landscape Density on Start : ${resources.configuration.densityDpi}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("shhan", "Landscape Density on Resume : ${resources.configuration.densityDpi}")
    }
}