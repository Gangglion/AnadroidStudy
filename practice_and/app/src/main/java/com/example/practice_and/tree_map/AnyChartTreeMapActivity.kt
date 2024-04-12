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

    // temp 들어오는 데이터 예시
    val temp1: List<GetData> = listOf(
        GetData("항목1", "70"),
        GetData("항목2", "70"),
        GetData("항목3", "10"),
        GetData("항목4", "30"),
    )
    val temp2: List<GetData> = listOf(
        GetData("항목1", "10"),
        GetData("항목2", "100"),
        GetData("항목3", "30"),
        GetData("항목4", "50"),
    )
    val temp3: List<GetData> = listOf(
        GetData("항목1", "63"),
        GetData("항목2", "50"),
        GetData("항목3", "25"),
        GetData("항목4", "38"),
    )
    val temp4: List<GetData> = listOf(
        GetData("항목1", "30"),
        GetData("항목2", "30"),
        GetData("항목3", "30"),
        GetData("항목4", "30"),
    )
    val temp5: List<GetData> = listOf(
        GetData("항목1", "79"),
        GetData("항목2", "53"),
        GetData("항목3", "53"),
        GetData("항목4", "53"),
    )
    val temp6: List<GetData> = listOf(
        GetData("항목1", "47"),
        GetData("항목2", "47"),
        GetData("항목3", "47"),
        GetData("항목4", "22"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_map)
        mContext = this
        val treeMap = findViewById<TreeMapGridLayout>(R.id.v_tree_map)
        treeMap.setData(temp6)
    }
}