package com.example.practice_and.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.example.practice_and.R
import com.example.practice_and.component.ChartMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.io.File
import java.io.FileOutputStream

class LineChartActivity : AppCompatActivity() {
    private lateinit var mChart: LineChart
    private lateinit var mContext: Context
    // MEMO : 예시데이터
    private val tmpDate = arrayOf(
        "23.07.20",
        "23.07.21",
        "23.07.22",
        "23.07.23",
        "23.07.24",
        "23.07.25",
        "23.07.26",
        "23.07.27",
        "23.07.28",
        "23.07.29",
        "23.07.30",
        "23.07.31",
    )
    private val tmpData = arrayOf(
        "4",
        "8",
        "0",
        "0",
        "3",
        "6",
        "14",
        "18",
        "10",
        "12",
        "13",
        "16",
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart)
        mContext = this
        mChart = findViewById(R.id.mp_linechart)

        initChart()

        // MEMO : 저기요 쇼츠에 쓰일 이미지 생성
//        saveDrawableAsPNG(R.drawable.helmat, "helmat")
//        saveDrawableAsPNG(R.drawable.helmat_no, "no_helmat")
    }

    private fun initChart() { // 인자 : items: ArrayList<HealthIndexItem>
//        val dateList = ArrayList<String>()
//        val scoreItem = ArrayList<String>()
//        for(item in items){
//            val date = (item.date.replace('-', '.')).substring(2,item.date.length)
//            dateList.add(date)
//            scoreItem.add(item.score)
//        }
        mChart.description.isEnabled = false
        mChart.axisLeft.isEnabled = false
        mChart.setTouchEnabled(true)
        mChart.setScaleEnabled(false)
        mChart.isDragEnabled = true
        mChart.setPinchZoom(true)

        mChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawAxisLine(true) // 축의 라인 그린다
            axisLineWidth = 1.5f
            setDrawGridLines(false) // 축의 그리드 그린다
            axisMinimum = -0.4f // 축 왼쪽 간격. 준 값이 x의 최소값이 되기 때문에 첫 값은 왼쪽에서 떨어지게 됨
            axisMaximum = tmpDate.size.toFloat()
            setDrawLabels(true)

//            valueFormatter = IndexAxisValueFormatter(dateList)
            // MEMO : 예시
            valueFormatter = IndexAxisValueFormatter(tmpDate)
        }
        mChart.axisLeft.apply {
            isEnabled = false
        }
        mChart.axisRight.apply {
            setDrawLabels(true) // 축의 라벨 노출
            setDrawGridLines(false) // 축의 그리드 그린다
            axisLineWidth = 1.5f // y축 width
        }
        mChart.legend.apply {
            isEnabled = false
        }
//        mChart.data = setData(scoreItem)
        // MEMO : 예시
        mChart.data = setData()

        mChart.invalidate()
    }

    // shhan(230725) : 차트에 들어갈 데이터 가공
    private fun setData(): LineData { // 인자 scoreItem: ArrayList<String>
        val values1 = ArrayList<Entry>()
        val dateMap = HashMap<Float, String>()
//        for(i in 0 until scoreItem.size){
//            values1.add(Entry(i.toFloat(), scoreItem[i].toFloat()))
//        }
        // MEMO : 예시 데이터
        for(data in tmpData.indices){
            values1.add(Entry(data.toFloat(), tmpData[data].toFloat()))
            dateMap[data.toFloat()] = tmpDate[data]
        }
        val set1 = LineDataSet(values1, "").apply{
            setDrawCircles(true)
            circleRadius = 5f
            setCircleColor(mContext.getColor(R.color.powder_blue500))
            color = mContext.getColor(R.color.powder_blue500)
            highLightColor = Color.TRANSPARENT
        }

        val chartData = LineData().apply{
            addDataSet(set1)
            setDrawValues(false)
        }

        mChart.apply{
            val max = 6f
            setVisibleXRangeMaximum(max) // X값의 범위를 max길이로 정해줌으로서 차트 스크롤 가능하게 함 - setData 이후에 나와야 적용됨
            marker = ChartMarkerView(mContext, R.layout.layout_marker, dateMap) // 마커 추가
        }

        return chartData
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun saveDrawableAsPNG(drawableResource: Int, fileName: String){
        val drawable = mContext.getDrawable(drawableResource)!!
        val bitmap = drawableToBitmap(drawable)
        saveBitmapAsPNG(bitmap, fileName)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap{
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    private fun saveBitmapAsPNG(bitmap: Bitmap, fileName: String){
        try{
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if(!directory.exists()){
                directory.mkdirs()
            }

            val file = File(directory, "$fileName.png")

            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}