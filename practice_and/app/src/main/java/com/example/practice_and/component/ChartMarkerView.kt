package com.example.practice_and.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import com.example.practice_and.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

// shhan(230725) : 그래프 마커 지정. 백그라운드는 마인드스트롱 팜빌에서 따옴. 색상은 powder_blue_50
@SuppressLint("ViewConstructor")
class ChartMarkerView(context: Context?, layoutResource: Int, private val dateMap: HashMap<Float, String>) : MarkerView(context, layoutResource) {
    private var mTvDate: TextView = findViewById(R.id.tv_date)
    private var mTvContent: TextView = findViewById(R.id.tv_marker)

    override fun draw(canvas: Canvas?) {
        canvas!!.translate(-(width/2).toFloat(), -height.toFloat()-20) // 점의 상단 중앙

        super.draw(canvas)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        mTvDate.text = dateMap[e!!.x]
        mTvContent.text = e.y.toInt().toString()

        super.refreshContent(e, highlight)
    }
}