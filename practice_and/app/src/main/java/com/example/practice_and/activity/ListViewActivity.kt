package com.example.practice_and.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.practice_and.R
import com.example.practice_and.adapter.ListViewAdapter

class ListViewActivity : AppCompatActivity() {
    companion object{
        const val SAMPLE_TEXT = "리스트뷰 %d 번째 항목"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        val adapter = ListViewAdapter(this, setData())
        findViewById<ListView>(R.id.lv_no_scroll).apply{
            setAdapter(adapter)
        }
    }

    private fun setData(): ArrayList<String>{
        val sampleData = ArrayList<String>()
        for(i in 1..100){
            sampleData.add(SAMPLE_TEXT.format(i))
        }

        return sampleData
    }
}