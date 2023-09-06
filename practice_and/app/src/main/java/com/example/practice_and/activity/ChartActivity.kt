package com.example.practice_and.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_and.R
import com.example.practice_and.fragment.BarChartFragment
import com.example.practice_and.component.BottomMenu

@RequiresApi(Build.VERSION_CODES.O)
class ChartActivity : AppCompatActivity() {

    private var mBottomMenu : BottomMenu? = null
    private var mContext : Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        mContext = this
        mBottomMenu = findViewById(R.id.bottom_menu)

        mBottomMenu!!.setFragmentManager(supportFragmentManager)
        // 기본 BarChart 나오게
        supportFragmentManager.beginTransaction().replace(R.id.fl_chart, BarChartFragment()).commit()

    }
}