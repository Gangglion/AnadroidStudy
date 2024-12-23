package com.example.practice_and.circular_progress

import android.animation.TimeInterpolator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import com.example.practice_and.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CircularProgressActivity : AppCompatActivity() {
    private lateinit var mPbCustom: CircularProgressView
    private lateinit var mPbView: ProgressBar
    private lateinit var mPbOpenSource: OpenSourceCircularProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_progress)
        mPbCustom = findViewById(R.id.pb_custom)
        mPbView = findViewById(R.id.pb_view)
        mPbOpenSource = findViewById(R.id.pb_open_source)
        mPbOpenSource.apply{
            roundBorder = true
            startAngle = 0f
            progressBarWidth = 20f
            backgroundProgressBarWidth = 20f
        }
    }

    // ex) 15초 지정 => 총 15개 -> 1초에 1개씩 // 150개 -> 0.1초에 1개씩 // 300개 -> 0.05초에 1개씩 => MEMO : 시간계산 필수
    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Default).launch{
            mPbCustom.max = 100F
            mPbView.max = 100
            mPbOpenSource.progressMax = 100F
            for(i in 1..100){
                delay(500L)
                Log.d("shhan", "진행중")
                CoroutineScope(Dispatchers.Main).launch {
                    mPbCustom.progress = i.toFloat()
                    mPbView.setProgress(i, true)
                    mPbOpenSource.setProgressWithAnimation(progress = i.toFloat(), 1000L)
                }
            }
        }
    }
}