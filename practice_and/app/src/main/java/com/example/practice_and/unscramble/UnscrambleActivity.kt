/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.practice_and.unscramble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.example.practice_and.App
import com.example.practice_and.R

/**
 * Creates an Activity that hosts the Game fragment in the app
 */
class UnscrambleActivity : AppCompatActivity() {
    var test = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unscramble)
        // 방법 2
        if(savedInstanceState != null){
            test = savedInstanceState.getInt("TEST_VALUE")
        }
        Log.d(App.TAG, "Call onCreate  // test = $test")
    }

    override fun onStart() {
        super.onStart()
        Log.d(App.TAG, "Call onStart")
    }

    // 방법 1
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(App.TAG, "Call onRestoreInstanceState")
//        test = savedInstanceState.getInt("TEST_VALUE")
    }

    override fun onResume() {
        super.onResume()
        test += 3
        Log.d(App.TAG, "Call onResume // test : $test")
    }

    override fun onPause() {
        super.onPause()
        Log.d(App.TAG, "Call onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(App.TAG, "Call onStop")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(App.TAG, "Call onSaveInstanceState")
        outState.putInt("TEST_VALUE", test)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(App.TAG, "Call onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(App.TAG, "Call onRestart")
    }
}