package com.example.practice_and.null_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practice_and.R

class NullTestActivity : AppCompatActivity() {
    private val mNullClass: NullClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null_test)


        Log.d("shhan", "onCreate Call")

        runApply()
    }

    private fun runApply(){
        Log.d("shhan", "runApply Function Call")
        mNullClass?.apply{
            Log.d("shhan", "?.apply call value : $tmpStr")
        }
    }
}

class NullClass(){
    val tmpStr: String = "Test"
    init{
        Log.d("shhan", "Init Run")
    }
}