package com.example.practice_and.pedometer_and_chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.practice_and.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class LineChartFragment : Fragment(), OnClickListener {
    private var mContext: Context? = null
    private var mBackPressedCallback: OnBackPressedCallback? = null
    private var mTvChartInfo: TextView? = null
    private var mLineChart: LineChart? = null
    private var mExTimeXAxis: List<String>? =
        listOf(
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00",
            "00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00"
        )
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(mContext, StepActivity::class.java)
                intent.putExtra("recent", mTvChartInfo!!.text ?: "No Chart")
                activity!!.setResult(9001, intent)
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, mBackPressedCallback!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_line_chart, container, false)
        mContext = context
        mTvChartInfo = rootView.findViewById(R.id.tv_chartinfo)
        rootView.findViewById<ImageView>(R.id.btn_refresh).setOnClickListener(this)

        mLineChart = rootView.findViewById(R.id.mp_linechart)
        mLineChart?.apply {
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.line_chart_bg))
            axisRight.isEnabled = false
            setTouchEnabled(true)
            setScaleEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
        }
        val xAxis = mLineChart?.xAxis
        xAxis?.apply {
            setDrawAxisLine(true) // 축의 라인 그린다
            setDrawGridLines(true) // 축의 그리드 그린다
            setDrawLabels(true) // 축의 라벨 노출
            axisMinimum = -0.4f // 축 왼쪽 간격. 준 값이 x의 최소값이 되기 때문에 첫 값은 왼쪽에서 떨어지게 됨
            enableGridDashedLine(20f, 10f, 0f)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IndexAxisValueFormatter(mExTimeXAxis)
        }
        val yAxis = mLineChart?.axisLeft
        yAxis?.apply {
            labelCount = 6
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            setDrawAxisLine(false) // 축의 라인 그린다
            setDrawGridLines(true) // 축을 그리드 그린다
            setDrawLabels(true) // 축의 라벨 노출
            enableGridDashedLine(20f, 10f, 0f)
            labelCount = 6
            granularity = 1f
        }
        val yRightAxis = mLineChart?.axisRight
        yRightAxis?.isEnabled = false

        val legend = mLineChart?.legend
        legend?.isEnabled = false

        mLineChart?.data = setData()
        mLineChart?.apply{
            val max = 6f // X값의 범위를 max길이로 정해줌으로서 차트 스크롤 가능하게 함
            setVisibleXRangeMaximum(max)
        }
        mLineChart?.invalidate()

        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        mBackPressedCallback!!.remove()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_refresh -> { // 차트 새로고침 - 왜 안돼지?
                Log.d("tmdguq", "RefreshBtn LineChart Click")
                mLineChart?.data = setData()
                mLineChart?.notifyDataSetChanged()
                mLineChart?.invalidate()
            }
        }
    }

    private fun setData(): LineData {
        val values1 = ArrayList<Entry>()
        for (i: Int in 0..19) {
            val randomVal = Random().nextInt(150)
            values1.add(Entry(i.toFloat(), randomVal.toFloat()))
        }
        val set1 = LineDataSet(values1, "").apply {
            setDrawCircleHole(true)
            circleHoleColor = Color.WHITE
            circleRadius = 7f
            circleHoleRadius = 4f
            lineWidth = 3f
            valueFormatter = DefaultValueFormatter(0)
        }
        set1.color = ContextCompat.getColor(mContext!!, R.color.line_color)
        val values2 = ArrayList<Entry>()
        for (i: Int in 0..19) {
            val randomVal = Random().nextInt(100) + 160
            values2.add(Entry(i.toFloat(), randomVal.toFloat()))
        }
        val set2 = LineDataSet(values2, "").apply {
            setDrawCircleHole(true)
            setCircleColor(Color.WHITE)
            circleRadius = 7f
            circleHoleRadius = 4f
            lineWidth = 3f
            valueFormatter = DefaultValueFormatter(0)
        }
        set2.color = ContextCompat.getColor(mContext!!, R.color.line_color)
        val colorsSet1: ArrayList<Int> = ArrayList()
        val colorsSet2: ArrayList<Int> = ArrayList()
        for(i in values1.indices){
            val minusVal = values2[i].y - values1[i].y
            if(minusVal<=100){
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_good))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_good))
            } else if (minusVal in 100.0..130.0){
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_fewDangerous))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_fewDangerous))
            } else{
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_dangerous))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_dangerous))
            }
        }
        set1.circleColors = colorsSet1
        set2.circleColors = colorsSet2

        val multiChartData = LineData().apply {
            addDataSet(set1)
            addDataSet(set2)
            setDrawValues(true)
        }
        return multiChartData
    }
}