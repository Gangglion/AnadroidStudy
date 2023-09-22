package com.example.practice_and.pedometer_and_chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
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
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@RequiresApi(Build.VERSION_CODES.O)
class CombineChartFragment : Fragment(), OnClickListener {
    private var mContext: Context? = null
    private var mBackPressedCallback: OnBackPressedCallback? = null
    private var mTvChartInfo: TextView? = null
    private var mCBChart: CombinedChart? = null
    private var valueOpen: HashMap<Int, Int>? = null
    private var valueClose: HashMap<Int, Int>? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        valueOpen = getOpenData()
        valueClose = getCloseData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_combine_chart, container, false)
        mContext = context
        mTvChartInfo = rootView.findViewById(R.id.tv_chartinfo)
        rootView.findViewById<ImageView>(R.id.btn_refresh).setOnClickListener(this)
        mCBChart = rootView.findViewById(R.id.mp_combinechart)

        // 데이터 생성

        // 차트 초기화
        chartInit()

        // 차트 그리기
        val data = CombinedData().apply{
            setData(generateLineDataSet())
            setData(getCandleDataSet())
        }
        mCBChart?.apply {
            this.data = data
            setVisibleXRangeMaximum(5f)
            invalidate()
        }
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        mBackPressedCallback!!.remove()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_refresh -> {
                // Refresh
            }
        }
    }

    // CombinedChart Initialize
    private fun chartInit() {
        mCBChart?.apply {
            description.isEnabled = false
            axisRight.isEnabled = false
            isDragEnabled = true
            isDoubleTapToZoomEnabled = false
            legend.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.line_chart_bg))
            setScaleEnabled(false)
            setPinchZoom(false)
            drawOrder = arrayOf(CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE)
        }
        mCBChart?.xAxis?.apply {
            setDrawAxisLine(false)
            enableGridDashedLine(20f, 10f, 0f)
            position = XAxis.XAxisPosition.BOTTOM
            labelCount = 5
            axisMinimum = -0.4f
            axisMaximum = 19.4f
        }
        mCBChart?.axisLeft?.apply {
            setDrawAxisLine(false)
            enableGridDashedLine(20f, 10f, 0f)
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            axisMinimum = 0f
            axisMaximum = 400f
        }
    }


    // 이중 선 그래프 생성 및 리턴
    private fun generateLineDataSet(): LineData {
        val entryOpen = ArrayList<Entry>()
        for (i: Int in 0..19) {
            entryOpen.add(Entry(i.toFloat(), valueOpen?.get(i)!!.toFloat()))
        }
        val setOpen = LineDataSet(entryOpen, "").apply {
            setDrawCircleHole(true)
            circleHoleColor = Color.WHITE
            circleRadius = 15f
            circleHoleRadius = 7f
            lineWidth = 3f
            color = ContextCompat.getColor(mContext!!, R.color.line_color)
        }
        val entryClose = ArrayList<Entry>()
        for (i: Int in 0..19) {
            entryClose.add(Entry(i.toFloat(), valueClose?.get(i)!!.toFloat()))
        }
        val setClose = LineDataSet(entryClose, "").apply {
            setDrawCircleHole(true)
            circleHoleColor = Color.WHITE
            circleRadius = 15f
            circleHoleRadius = 7f
            lineWidth = 3f
            color = ContextCompat.getColor(mContext!!, R.color.line_color)
        }
        val colorsSet1: ArrayList<Int> = ArrayList()
        val colorsSet2: ArrayList<Int> = ArrayList()
        for(i in 0..19){
            val closeVal = valueClose!![i]
            val openVal = valueOpen!![i]
            val minusVal = closeVal!!.minus(openVal!!)
            if(minusVal<=120){
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_good))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_good))
            } else if (minusVal in 120..150){
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_fewDangerous))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_fewDangerous))
            } else{
                colorsSet1.add(ContextCompat.getColor(mContext!!, R.color.line_dangerous))
                colorsSet2.add(ContextCompat.getColor(mContext!!, R.color.line_dangerous))
            }
        }
        setOpen.circleColors = colorsSet1
        setClose.circleColors = colorsSet2
        val multiLineData = LineData().apply {
            addDataSet(setOpen)
            addDataSet(setClose)
            setDrawValues(false)
        }
        return multiLineData
    }

    // get Candle Data
    private fun getCandleDataSet(): CandleData {
        val entries = ArrayList<CandleEntry>()
        for (i: Int in 0..19) {
            entries.add(
                CandleEntry(
                    i.toFloat(),
                    0f,
                    0f,
                    valueOpen!![i]!!.toFloat(),
                    valueClose!![i]!!.toFloat()
                )
            )
        }
        val dataSet = CandleDataSet(entries, "").apply{
            // 양봉
            increasingColor = ContextCompat.getColor(mContext!!, R.color.line_good)
            increasingPaintStyle = Paint.Style.FILL
            barSpace = 0.3f
        }
        return CandleData(dataSet)
    }
//    private fun generateCandleData(): CandleData {
//
//    }

    private fun getOpenData(): HashMap<Int, Int> {
        val hashMap = HashMap<Int, Int>()
        for (i: Int in 0..19) {
            val randomVal = Random().nextInt(130) + 50
            hashMap[i] = randomVal
        }
        return hashMap
    }

    private fun getCloseData(): HashMap<Int, Int> {
        val hashMap = HashMap<Int, Int>()
        for (i: Int in 0..19) {
            val randomVal = Random().nextInt(250) + 150
            hashMap[i] = randomVal
        }
        return hashMap
    }
}