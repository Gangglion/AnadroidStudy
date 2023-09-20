package com.example.practice_and.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practice_and.R

class InflateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflate)

        val frContainer = findViewById<FrameLayout>(R.id.fr_layout)
        val llContainter = findViewById<LinearLayoutCompat>(R.id.ll_layout)
        val rlContainter = findViewById<RelativeLayout>(R.id.rl_layout)
        val clContainter = findViewById<ConstraintLayout>(R.id.cl_layout)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        findViewById<AppCompatButton>(R.id.btn_go_main).apply{
            setOnClickListener { finish() }
        }

        findViewById<AppCompatButton>(R.id.btn_frameLayout).apply {
            setOnClickListener {
                // attachToRooT 를 false로 주어 바로 붙이지 않았을때
                val inflateView = inflater.inflate(R.layout.layout_inflate, frContainer, false)
                frContainer.addView(inflateView) // 컨테이너에 붙이는 작업
                inflateView.findViewById<AppCompatTextView>(R.id.inflate_string).text = "프레임레이아웃 inflate"
            }
        }

        findViewById<AppCompatButton>(R.id.btn_linearlayout).apply{
            setOnClickListener {
                inflater.inflate(R.layout.layout_inflate, llContainter, true).findViewById<AppCompatTextView>(R.id.inflate_string).text = "리니어레이아웃 inflate"
            }
        }
        
        findViewById<AppCompatButton>(R.id.btn_relativelayout).apply{
            setOnClickListener {
                inflater.inflate(R.layout.layout_inflate, rlContainter, true).findViewById<AppCompatTextView>(R.id.inflate_string).text = "렐러티브레이아웃 inflate"
            }
        }

        findViewById<AppCompatButton>(R.id.btn_constraint).apply{
            setOnClickListener {
                inflater.inflate(R.layout.layout_inflate, clContainter, true).findViewById<AppCompatTextView>(R.id.inflate_string).text = "ConstraintLayout inflate"
            }
        }
    }
}