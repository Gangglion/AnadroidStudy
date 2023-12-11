package com.example.mvvmactivity.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmactivity.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO : 리사이클러 뷰 보이고, 누르면 색 변경, 누르는 항목마다 Realm 에 저장. 하단 버튼 누르면 Fragment 전환되고, Realm 조회한 결과를 TestView 에 뿌려줌
    }
}