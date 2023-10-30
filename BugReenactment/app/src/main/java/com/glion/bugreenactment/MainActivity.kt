package com.glion.bugreenactment

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mContext: Context
    companion object{
        const val A = 0
        const val B = 1
        const val C = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        findViewById<AppCompatButton>(R.id.btn_a).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_b).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_c).setOnClickListener(this)

        Log.w(TAG, "MainActivity - onCreate")

        changeFragment(1)
    }

    override fun onStart() {
        super.onStart()
        Log.w(TAG, "MainActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "MainActivity - onResume")
        Log.d(TAG, "MainActivity : ${resources.configuration.densityDpi}")
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "MainActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w(TAG, "MainActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "MainActivity - onDestroy")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_a -> {
                changeFragment(A)
            }
            R.id.btn_b ->{
                changeFragment(B)
            }
            R.id.btn_c ->{
                changeFragment(C)
            }
        }
    }

    private fun changeFragment(tab: Int){
        val fragment: Fragment = when(tab){
            A -> AFragment()
            B -> BFragment()
            C -> CFragment()
            else -> AFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fv_container, fragment).commit()
    }

}