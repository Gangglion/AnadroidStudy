package com.example.practice_and.saveinstance

import android.os.Bundle
import android.util.Log
import com.example.practice_and.App
import com.example.practice_and.R
import com.google.android.material.textfield.TextInputEditText

class SaveInstanceActivity : BaseActivity() {
    private var userInput: String? = null
    private lateinit var etInput : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(App.TAG + " : SaveInstanceActivity", "onCreate")
        setContentView(R.layout.activity_save_instance)
        etInput = findViewById(R.id.et_input)

        if(savedInstanceState != null){
            val saveString = savedInstanceState.getString("INPUT")
            etInput.setText(saveString)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(App.TAG + " : SaveInstanceActivity", "onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(App.TAG + " : SaveInstanceActivity", "onRestoreInstanceState")
        val saveString = savedInstanceState.getString("INPUT") // key INPUT 으로 저장된 문자열 가져오기
        etInput.setText(saveString) // EditText에 세팅
    }

    override fun onResume() {
        super.onResume()
        Log.d(App.TAG + " : SaveInstanceActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(App.TAG + " : SaveInstanceActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(App.TAG + " : SaveInstanceActivity", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(App.TAG + " : SaveInstanceActivity", "onSaveInstanceState")
        outState.putString("INPUT", etInput.text.toString()) // key INPUT 으로 editText에 입력한 문자열 저장.
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(App.TAG + " : SaveInstanceActivity", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(App.TAG + " : SaveInstanceActivity", "onRestart")
    }
}