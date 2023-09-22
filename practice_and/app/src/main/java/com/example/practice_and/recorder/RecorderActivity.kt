package com.example.practice_and.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.MediaRecorder.AudioSource.MIC
import android.media.MediaRecorder.OutputFormat.MPEG_2_TS
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_MUSIC
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.toString
import com.example.practice_and.databinding.ActivityRecorderBinding
import java.io.File
import java.time.LocalDateTime
import java.util.*


@RequiresApi(Build.VERSION_CODES.S)
class RecorderActivity : AppCompatActivity() {
    companion object{
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    private lateinit var mBinding : ActivityRecorderBinding
    private var mContext = this
    private var isRecording: Boolean = false
    private var mMediaRecorder: MediaRecorder? = null
    private var isPermissionAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var mTimerTask: TimerTask? = null
    private var mTimer: Timer? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_RECORD_AUDIO_PERMISSION ->{
                if(grantResults.isNotEmpty()){
                    isPermissionAccepted = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRecorderBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if(ContextCompat.checkSelfPermission(mContext, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            isPermissionAccepted = true
        } else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        }

        mBinding.ivRecorderStatus.setOnClickListener {
            if(isPermissionAccepted){
                if(!isRecording){
                    // 녹음 시작
                    startRecording()
                } else{
                    stopRecording()
                }
            } else{
                Toast.makeText(mContext, "권한설정이 완료되지 않아 녹음을 시작할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecording(){
        val path = Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).absolutePath+"/"+getString(R.string.app_name) // 앱이름으로 바로 폴더생성은 안되는것일까?
        val file = File(path)
        if(!file.exists()){
            file.mkdirs()
        }
        mMediaRecorder = MediaRecorder(mContext).apply{
            setAudioSource(MIC)
            setOutputFormat(MPEG_2_TS)
            setOutputFile("$path/v_${LocalDateTime.now().toString(App.FILE_DATE_FORMAT)}_voice.mp3")
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            try{
                prepare()
                start()
                isRecording = true
                mBinding.ivRecorderStatus.setImageResource(R.drawable.baseline_fiber_manual_record_on)
                Toast.makeText(mContext, getString(R.string.recorde_start), Toast.LENGTH_SHORT).show()
                startTimer()
            } catch(e: Exception){
                Log.e(App.TAG, "prepare() failed")
            }
        }
    }

    private fun stopRecording(){
        mMediaRecorder!!.apply{
            stop()
            release()
            // 녹음 종료
            isRecording = false
            mBinding.ivRecorderStatus.setImageResource(R.drawable.baseline_fiber_manual_record_off)
            Toast.makeText(mContext, getString(R.string.recorde_end), Toast.LENGTH_SHORT).show()
            endTimer()
        }
    }

    private fun startTimer(){
        mTimer = Timer()
        mTimerTask = object: TimerTask(){
            var count = 0
            override fun run() {
                count++
                mBinding.tvRunningTime.post{
                    run {
                        if(count<10) {
                            mBinding.tvRunningTime.text = "00 : 0$count"
                        } else if(count == 60){
                            mBinding.tvRunningTime.text = "01 : 00"
                            stopRecording()
                        } else{
                            mBinding.tvRunningTime.text = "00 : $count"
                        }
                        mBinding.pbLoading.progress = count
                    }
                }
            }

        }
        mTimer!!.schedule(mTimerTask,0, 1000)
    }
    private fun endTimer(){
        if(mTimerTask != null){
            mBinding.tvRunningTime.text = mContext.getString(R.string.default_timer)
            mTimerTask!!.cancel()
            mTimerTask = null
            mBinding.pbLoading.progress = 0
        }
    }
}