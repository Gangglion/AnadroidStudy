package com.example.practice_and.component

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.example.practice_and.R
import com.example.practice_and.databinding.LayoutQrSnackbarBinding
import com.google.android.material.snackbar.Snackbar

class QrSnackbar(view: View, private val url: String) {
    companion object{
        fun make(view: View, url: String) = QrSnackbar(view, url)
    }

    private val mContext = view.context
    private val mSnackbar = Snackbar.make(view, "", 10000)
    private val mSnackbarLayout = mSnackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(mContext)
    private val mBinding = LayoutQrSnackbarBinding.inflate(inflater)

    init{
        initView()
        initData()
    }

    private fun initView(){
        with(mSnackbarLayout){
            removeAllViews()
            setPadding(0,0,0,16)
            setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent))
            addView(mBinding.root)
        }
    }

    private fun initData(){
        mBinding.tvUrl.text = mContext.getString(R.string.url_view).format(url)

        mBinding.llOpen.setOnClickListener {
            // TODO : 브라우저 선택 -> 선택한 브라우저 앱으로 링크 열기
        }
    }

    fun show(){
        mSnackbar.show()
    }

    fun isShow(): Boolean{
        return mSnackbar.isShown
    }
}