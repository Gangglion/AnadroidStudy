package com.example.practice_and.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.practice_and.databinding.LayoutCustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val message: String, private val btnStr: String) {

    companion object{
        fun make(view: View, message: String, btnStr: String) = CustomSnackBar(view, message, btnStr)
    }

    private var mContext = view.context
    private val mSnackbar = Snackbar.make(view, "", 5000)
    private val mSnackbarLayout = mSnackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(mContext)
    private val mBinding = LayoutCustomSnackbarBinding.inflate(inflater)


    init{
        initView()
        initData()
    }

    private fun initView(){
        with(mSnackbarLayout) {
            removeAllViews()
            setPadding(16, 0, 16, 16)
            setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent))
            addView(mBinding.root)
        }
    }

    private fun initData(){
        mBinding.tvInfo.text = message
        mBinding.tvCustomBtn.text= btnStr

        mBinding.tvCustomBtn.setOnClickListener {
            when(btnStr){
                "토스트"->{
                    Toast.makeText(mContext, "토스트 띄우기 완료", Toast.LENGTH_SHORT).show()
                    mSnackbar.dismiss()
                }
                "권한허용"->{
                    gotoSetting()
                    mSnackbar.dismiss()
                }
            }
        }
    }

    private fun gotoSetting(){
        val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS, Uri.fromParts("package", mContext.packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }

    fun show(){
        mSnackbar.show()
    }
}