package com.example.practice_and

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.chibatching.kotpref.Kotpref
import com.example.practice_and.pedometer_and_chart.RealmClass

class App : Application(){
    companion object{
        const val TAG = "shhan"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val FILE_DATE_FORMAT = "yyyyMMddHHmmss"

        lateinit var instance: App private set
    }
    private var toast: Toast? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "ApplicationClass Run")
        // Kotpref Initialize
        Kotpref.init(this)
        if(RealmClass.realm == null){
            // Realm Initialize
            RealmClass.initialize(this)
        }

        instance = this
    }
    fun makeToast(msg: String){
        toast?.cancel()
        toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }
}