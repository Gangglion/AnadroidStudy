package com.glion.testgather.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glion.testgather.R
import com.glion.testgather.databinding.ActivityMainBinding
import com.glion.testgather.scope_func.ScopeFunction

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnScope.apply{
            setOnClickListener {
                startActivity(Intent(context, ScopeFunction::class.java))
            }
        }
        supportFragmentManager.beginTransaction()
    }
}