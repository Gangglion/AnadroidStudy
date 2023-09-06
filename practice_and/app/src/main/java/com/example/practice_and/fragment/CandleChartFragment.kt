package com.example.practice_and.fragment

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
import com.example.practice_and.activity.StepActivity
import com.example.practice_and.data.CandleChartExDataUtil
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry

@RequiresApi(Build.VERSION_CODES.O)
class CandleChartFragment : Fragment(), OnClickListener {
    private var mContext: Context? = null
    private var mBackPressedCallback: OnBackPressedCallback? = null
    private var mTvChartInfo: TextView? = null
    private var mCSChart: CandleStickChart? = null

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_candle_chart, container, false)
        mContext = context
        mTvChartInfo = rootView.findViewById(R.id.tv_chartinfo)
        rootView.findViewById<ImageView>(R.id.btn_refresh).setOnClickListener(this)
        mCSChart = rootView.findViewById(R.id.mp_candlechart)
        // 차트 초기화
        csChartInit()
        // 차트 그리기
        drawChart()
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        mBackPressedCallback!!.remove()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_refresh->{

            }
        }
    }

    // Chart Initialize
    private fun csChartInit(){
        mCSChart?.axisLeft?.apply{
            setDrawAxisLine(false)
            setDrawGridLines(true)
            enableGridDashedLine(20f,10f,0f)
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            mAxisMinimum = 200f
            mAxisMaximum = 232f
        }
        mCSChart?.xAxis?.apply{
            position = XAxis.XAxisPosition.BOTTOM
            enableGridDashedLine(20f,10f,0f)
            labelCount = 10
            axisMinimum = -0.4f
        }
        mCSChart?.apply{
            setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.line_chart_bg))
            description.isEnabled = false
            axisRight.isEnabled = false
            legend.isEnabled = false
            isHighlightPerDragEnabled = true
            isDragEnabled = true
            isDoubleTapToZoomEnabled = false
            setTouchEnabled(true)
            setScaleEnabled(false)
            setDrawGridBackground(false)
        }
    }

    // get Data
    private fun getChartData(): ArrayList<CandleEntry> {
        val entries = ArrayList<CandleEntry>()
        for(value in CandleChartExDataUtil.getExData()){
            entries.add(CandleEntry(
                value.createdAt.toFloat(),
                value.shadowHigh,
                value.shadowLow,
                value.open,
                value.close
            ))
        }
        return entries
    }

    // draw Chart
    private fun drawChart(){
        val dataSet = CandleDataSet(getChartData(), "").apply{
            // 심지 부분
            shadowColor = Color.BLACK
            shadowWidth = 1f

            // 음봉
            decreasingColor = Color.BLUE
            decreasingPaintStyle = Paint.Style.FILL

            // 양봉
            increasingColor = Color.RED
            increasingPaintStyle = Paint.Style.FILL

            neutralColor = Color.LTGRAY
            setDrawValues(false)
            // 터치 시 노란선 제거
            highLightColor = Color.TRANSPARENT

        }
        mCSChart?.apply{
            data = CandleData(dataSet)
            setVisibleXRangeMaximum(10f) // 차트의 x값 최대치를 줌으로서 차트가 스크롤 될 수 있게 함
            invalidate()
        }
    }
}