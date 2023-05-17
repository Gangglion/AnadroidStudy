package com.glion.testgather.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glion.testgather.R
import com.glion.testgather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportFragmentManager.beginTransaction()
    }
}