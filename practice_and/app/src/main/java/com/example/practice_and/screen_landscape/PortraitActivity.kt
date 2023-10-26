package com.example.practice_and.screen_landscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.practice_and.App
import com.example.practice_and.R

class PortraitActivity : AppCompatActivity(), OnClickListener {
    companion object{
        const val A = 0
        const val B = 1
        const val C = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.PortraitTheme)
        setContentView(R.layout.activity_portrait)

        findViewById<AppCompatButton>(R.id.btn_a).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_b).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_c).setOnClickListener(this)

        Log.w(App.TAG, "PortraitActivity - onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.w(App.TAG, "PortraitActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w(App.TAG, "PortraitActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w(App.TAG, "PortraitActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w(App.TAG, "PortraitActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(App.TAG, "PortraitActivity - onDestroy")
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