package com.example.mvvmactivity.ui.recyclerview.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ItemTempBinding
import com.example.mvvmactivity.ui.recyclerview.model.TempData


class TempAdapter(
    private val dataSet: List<TempData>
) :
    RecyclerView.Adapter<TempAdapter.ViewHolder>() {
    private lateinit var mBinding: ItemTempBinding

    class ViewHolder(val binding: ItemTempBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_temp, viewGroup, false)

        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.item = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

}
