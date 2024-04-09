package com.example.practice_and.tree_map

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart.treeMap
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.TreeDataEntry
import com.anychart.core.ui.Title
import com.anychart.enums.SelectionMode
import com.anychart.enums.TreeFillingMethod
import com.example.practice_and.R


class AnyChartTreeMapActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_map)
        mContext = this
    }
}