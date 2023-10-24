package com.example.practice_and

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.practice_and.calendar.CalendarActivity
import com.example.practice_and.copywebtoon.CopyWebtoonActivity
import com.example.practice_and.inflate.InflateActivity
import com.example.practice_and.linechart_prac.LineChartActivity
import com.example.practice_and.listview.ListViewActivity
import com.example.practice_and.qrmlkit.QrMlkitActivity
import com.example.practice_and.recyclerview.RecyclerViewActivity
import com.example.practice_and.webview.WebviewActivity
import com.example.practice_and.googlefitness.FitnessFragment
import com.example.practice_and.hidekeyboard.InputProcessFragment
import com.example.practice_and.null_safety.NullSafetyActivity
import com.example.practice_and.thread.ThreadFragment
import com.example.practice_and.pedometer_and_chart.StepActivity
import com.example.practice_and.qrzxing.QrZxingActivity
import com.example.practice_and.recorder.RecorderActivity
import com.example.practice_and.saveinstance.SaveInstanceActivity
import com.example.practice_and.stt.STTActivity
import com.example.practice_and.stt_record.SttRecorderActivity
import com.example.practice_and.unscramble.UnscrambleActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mBackPressed: OnBackPressedCallback
    private var isDoubleBackPressed: Boolean = false
    private var mContext = this
    private lateinit var mView: View
    private lateinit var mActivityArea: ConstraintLayout


    // Floating Action Button(FAB)
    private lateinit var mFabOpen: Animation
    private lateinit var mFabClose: Animation
    private var isFabOpen = false
    private lateinit var mFab: FloatingActionButton
    private lateinit var mFab1: FloatingActionButton
    private lateinit var mFab2: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mView = findViewById(R.id.rootview)
        mActivityArea = findViewById(R.id.activity_area)

        tokenUpdate()

        mBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isDoubleBackPressed) {
                    // 앱 완전 종료.
                    ActivityCompat.finishAffinity(this@MainActivity)
                    exitProcess(0)
                } else {
                    isDoubleBackPressed = true
                    Toast.makeText(
                        mContext,
                        getString(R.string.close_main_ready),
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed(
                        { isDoubleBackPressed = false },
                        2000
                    )
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(mBackPressed)
        findViewById<AppCompatButton>(R.id.btn_step).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_webview).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_voice).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_snack).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_stt).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_qr).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_qrmlkit).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_stt_recorde).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_recyclerview).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_calendar).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_listview).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_linechart).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_fitness).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_input).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_webtoon).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_async).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_inflate).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_viewmodel).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_saveinstance).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_null).setOnClickListener(this)

        // Enum Class 사용 테스트
        Log.d(App.TAG, "Enum parameter : ${AppTempData.FIRST.parameter} / Enum name : ${AppTempData.FIRST.name} / Enum constant : ${AppTempData.FIRST}")
        Log.d(App.TAG, "Enum parameter : ${AppTempData.SECOND.parameter} / Enum name : ${AppTempData.SECOND.name} / Enum constant : ${AppTempData.SECOND}")
        Log.d(App.TAG, "Enum parameter : ${AppTempData.THIRD.parameter} / Enum name : ${AppTempData.THIRD.name} / Enum constant : ${AppTempData.THIRD}")
        Log.d(App.TAG, "Enum parameter : ${AppTempData.FOURTH.parameter} / Enum name : ${AppTempData.FOURTH.name} / Enum constant : ${AppTempData.FOURTH}")
        Log.d(App.TAG, "Enum parameter : ${AppTempData.FIFTH.parameter} / Enum name : ${AppTempData.FIFTH.name} / Enum constant : ${AppTempData.FIFTH}")
        Log.d(App.TAG, "Parameter Combine : ${AppTempData.FIRST.getParameterCombine()}")
        Log.d(App.TAG, "Constant Combine : ${AppTempData.SECOND.useThisCombine()}")

        // Floating Action Button(FAB)
        mFabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        mFabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        mFab = findViewById<FloatingActionButton>(R.id.fab).apply{
            setOnClickListener(this@MainActivity)
        }
        mFab1 = findViewById<FloatingActionButton>(R.id.fab1).apply{
            setOnClickListener(this@MainActivity)
        }
        mFab2 = findViewById<FloatingActionButton>(R.id.fab2).apply{
            setOnClickListener(this@MainActivity)
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_step -> {
                val intent = Intent(this, StepActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_webview -> {
                val intent = Intent(this, WebviewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_voice -> {
                val intent = Intent(this, RecorderActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_stt->{
                val intent = Intent(this, STTActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_snack->{
                CustomSnackBar.make(mView, "스낵바 연습.", "토스트").show()
            }
            R.id.btn_qr->{
                val intent = Intent(this, QrZxingActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_qrmlkit->{
                startActivity(Intent(this, QrMlkitActivity::class.java))
            }
            R.id.btn_stt_recorde->{
                val intent = Intent(this, SttRecorderActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_recyclerview ->{
                val intent = Intent(this, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_calendar ->{
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_listview ->{
                val intent = Intent(this, ListViewActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_linechart ->{
                val intent = Intent(this, LineChartActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_fitness ->{
                mActivityArea.visibility = GONE
                val fragment = FitnessFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_view, fragment).commit()
            }
            R.id.btn_input ->{
                mActivityArea.visibility = GONE
                val fragment = InputProcessFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_view, fragment).commit()
            }
            R.id.btn_webtoon ->{
                startActivity(Intent(mContext, CopyWebtoonActivity::class.java))
            }
            R.id.btn_async ->{
                mActivityArea.visibility = GONE
                val fragment = ThreadFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_view, fragment).commit()
            }
            R.id.btn_inflate ->{
                startActivity(Intent(mContext, InflateActivity::class.java))
            }
            R.id.btn_viewmodel ->{
                startActivity(Intent(mContext, UnscrambleActivity::class.java))
            }
            R.id.btn_saveinstance ->{
                startActivity(Intent(mContext, SaveInstanceActivity::class.java))
            }
            R.id.btn_null ->{
                startActivity(Intent(mContext, NullSafetyActivity::class.java))
            }

            // Floating Action Button
            R.id.fab->{
                anim()
                Toast.makeText(this, "플로팅 버튼 클릭", Toast.LENGTH_SHORT).show()
            }
            R.id.fab1->{
                anim()
                Toast.makeText(this, "버튼 1 클릭", Toast.LENGTH_SHORT).show()
            }
            R.id.fab2->{
                anim()
                Toast.makeText(this, "버튼 2 클릭", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun anim(){
        isFabOpen = if (isFabOpen) {
            mFab1.startAnimation(mFabClose);
            mFab2.startAnimation(mFabClose);
            mFab1.isClickable = false;
            mFab2.isClickable = false;
            false;
        } else {
            mFab1.startAnimation(mFabOpen);
            mFab2.startAnimation(mFabOpen);
            mFab1.isClickable = true;
            mFab2.isClickable = true;
            true;
        }
    }

    fun exitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        mActivityArea.visibility = View.VISIBLE
    }

    fun tokenUpdate(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful){
                val token = it.result
                with(token){
                    Log.d("shhan", "Token : $token")
                }
            }
        }
    }
}