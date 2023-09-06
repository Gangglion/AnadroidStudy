package com.example.practice_and.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.fragment.ErrorPageFragment
import com.example.practice_and.fragment.ExFunctionFragment
import com.example.practice_and.fragment.WebviewFragment

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("CommitTransaction")
class WebviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        callWebviewFragment()
    }

    fun callWebviewFragment(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<WebviewFragment>(R.id.fragment_container_view)
        }
    }

    fun callErrorFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ErrorPageFragment>(R.id.fragment_container_view)
        }
    }

    fun callExFuncFragment(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ExFunctionFragment>(R.id.fragment_container_view)
        }
    }

    fun exitWebview(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            101->{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                    Log.d(App.TAG, "권한 승인됨")
                } else{
                    Log.d(App.TAG, "권한 거절됨")
                }
            }
        }
    }
}