package com.glion.bugreenactment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView


class DialogTextOneButton(a_context: Context, private val listener: OnConfirmClick) : Dialog(a_context), View.OnClickListener{

    interface OnConfirmClick{
        fun clickConfirm()
    }
    var mTvConfirm: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_one_button)
        setCancelable(false)

        mTvConfirm = findViewById(R.id.tv_confirm)
        mTvConfirm!!.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_confirm -> {
                confirm()
            }
        }
    }


    private fun confirm(){
        listener.clickConfirm()
        dismiss()
    }
}