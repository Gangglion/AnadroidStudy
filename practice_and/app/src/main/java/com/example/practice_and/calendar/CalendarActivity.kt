package com.example.practice_and.calendar

import android.os.Bundle
import com.example.practice_and.R
import com.example.practice_and.screen_landscape.BaseActivity

class CalendarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }
}