package com.example.practice_and.webview

import android.webkit.JavascriptInterface

class AndroidBridge {
    private var callback: OnRequestToast? = null

    interface OnRequestToast {
        fun showToast(msg: String)
        fun checkPermission()
        fun setAlarm()
    }

    fun setListener(listener: OnRequestToast) {
        callback = listener
    }

    @JavascriptInterface
    fun makeToast(msg: String) {
        callback?.showToast(msg)
    }

    @JavascriptInterface
    fun checkPermission(){
        callback?.checkPermission()
    }

    @JavascriptInterface
    fun setAlarm(){
        callback?.setAlarm()
    }
}
