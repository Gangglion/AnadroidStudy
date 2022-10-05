package com.example.mvvmactivity.ViewModel

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.example.mvvmactivity.Model.NoBindingModel
import com.example.mvvmactivity.R

class NoBindingViewModel(activity : Activity) {
    private var activity : Activity
    private var noBindingModel : NoBindingModel
    private lateinit var textView : TextView
    private lateinit var btn : Button

    init {
        this.activity = activity
        this.noBindingModel = NoBindingModel()
        initView()
    }

    private fun initView()
    {
        btn = activity.findViewById(R.id.textBtn)
        textView = activity.findViewById(R.id.text_view)
        btn.setOnClickListener { textView.setText(noBindingModel.clickedButton()) }
    }
}