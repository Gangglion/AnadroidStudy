package com.example.mvvmactivity.ui.recyclerview.adapter

import android.view.View
import com.example.mvvmactivity.ui.recyclerview.model.TempData

interface OnItemClickListener {
    fun onClick(view: View, tempData: TempData)
}