package com.example.mvvmactivity.viewModel

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.example.mvvmactivity.model.Model
import com.example.mvvmactivity.R

class ViewModel(activity : Activity) {
    private var activity : Activity
    private var model : Model
    private lateinit var textView : TextView
    private lateinit var btn : Button

    init {
        this.activity = activity
        this.model = Model()
        initView()
    }

    private fun initView()
    {
        btn = activity.findViewById(R.id.textBtn)
        textView = activity.findViewById(R.id.text_view)
        btn.setOnClickListener { textView.setText(model.clickedButton()) }
    }
}