package com.example.practice_and.null_safety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practice_and.App
import com.example.practice_and.R

class NullSafetyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null_safety)

        val nullableList: MutableList<String?> = mutableListOf("hello", null, "Kotlin", null, "test")
        var nullableStr: String? = null
        // Check Nullable
        // 1.
        if(nullableStr != null){
            Log.d(App.TAG, "Result : ${nullableStr.length}")
        } else{
            Log.d(App.TAG, "Result : null")
        }

        // 2.
        Log.d(App.TAG, "Result : ${nullableStr?.length}")

        // ?.let{  }
        for(i in nullableList.indices){
            nullableList[i]?.let{ Log.d(App.TAG, "${nullableList[i]} is not null")}
        }

        // Elvis Operator
        // 1.
//        val elvisStr: String = if(nullStr == null){
//            "this is Not Null"
//        } else {
//            nullStr
//        }
//        Log.d(App.TAG, "elvis Result : $elvisStr")

        // 2.
        val elvisStr = nullableStr?: "elvis change nullStr value not null"
        Log.d(App.TAG, elvisStr)

        // !!
        if(App.TAG == "shhan"){
            nullableStr = "Not Null"
        }
        Log.d(App.TAG, "${nullableStr!!.length}")

        // ClassCastException Prevention
        val tmpStr = "String"
        var tmpInt: Int?
        // 1. use try-catch
//        try{
//            tmpInt = tmpStr as Int
//        } catch(e: ClassCastException){
//            tmpInt = null
//        }
        // 2. Safe Cast with ?
        tmpInt = tmpStr as? Int
        Log.d(App.TAG, "Cast Result : ${tmpInt}")
    }
}