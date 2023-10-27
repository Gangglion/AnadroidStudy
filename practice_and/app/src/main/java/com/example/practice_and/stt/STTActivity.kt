package com.example.practice_and.stt

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practice_and.App
import com.example.practice_and.BaseActivity
import com.example.practice_and.R
import com.example.practice_and.CustomSnackBar
import com.example.practice_and.databinding.ActivitySttactivityBinding

class STTActivity : BaseActivity() {
    companion object{
        private const val PERMISSION_CODE = 1
    }

    private var mBinding: ActivitySttactivityBinding? = null
    private lateinit var mContext: Context
    private var isPermissionGranted = false
    private var mPermissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var isSttRunning = false

    private lateinit var speechRecognizer: SpeechRecognizer
    private var mIntent: Intent? = null

    private val mListener = object: RecognitionListener{
        override fun onReadyForSpeech(params: Bundle?) {
            Log.d(App.TAG, "사용자 말할 준비 완료")
        }

        override fun onBeginningOfSpeech() {
            Log.d(App.TAG, "사용자가 말하기 시작")
        }

        override fun onRmsChanged(rmsdB: Float) {

        }

        override fun onBufferReceived(buffer: ByteArray?) {

        }

        override fun onEndOfSpeech() {
            Log.d(App.TAG, "사용자가 말을 멈추면 호출됨. 인식 결과에 따라 onError나 onResult 가 호출됨")
        }

        override fun onError(error: Int) {
            var message = ""
            when(error){
                SpeechRecognizer.ERROR_AUDIO->{
                    message = "오디오 에러";
                }
                SpeechRecognizer.ERROR_CLIENT->{
                    message = "클라이언트 에러";
                }
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS->{
                    message = "퍼미션 없음";
                }
                SpeechRecognizer.ERROR_NETWORK->{
                    message = "네트워크 에러";
                }
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT->{
                    message = "네트워크 타임아웃";
                }
                SpeechRecognizer.ERROR_NO_MATCH->{
                    if(isSttRunning){
                        startRecording()
                    }
                }
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY->{
                    message = "바쁜 Recognizer";
                }
                SpeechRecognizer.ERROR_SERVER->{
                    message = "서버 에러";
                }
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT->{
                    message = "말하는 시간 초과";
                }
                else->{
                    message = "알 수 없는 오류"
                }
            }
            Log.d(App.TAG, message)
        }

        @SuppressLint("SetTextI18n")
        override fun onResults(results: Bundle?) {
            // 인식 결과가 준비되면 호출됨
            val matches: java.util.ArrayList<String>? = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val originText = mBinding!!.tvSttResult.text.toString()
            var newText = ""
            for(i in matches!!.indices){
                newText += i
            }
            Log.d(App.TAG, newText)
            mBinding!!.tvSttResult.text = "$originText $newText "
            speechRecognizer.startListening(mIntent)
        }

        override fun onPartialResults(partialResults: Bundle?) {

        }

        override fun onEvent(eventType: Int, params: Bundle?) {

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.isNotEmpty()){
                    isPermissionGranted = true
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                        isPermissionGranted = false
                }
                if(!isPermissionGranted){
                    CustomSnackBar.make(mBinding!!.root, "권한이 허용되지 않았습니다.", "권한허용").show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySttactivityBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        mContext = this

        if(ContextCompat.checkSelfPermission(mContext, mPermissions[0]) == PackageManager.PERMISSION_GRANTED){
            isPermissionGranted = true
        } else{
            ActivityCompat.requestPermissions(this, mPermissions, PERMISSION_CODE)
        }

        mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent!!.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        mIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        mBinding!!.ivRecorderStatus.setOnClickListener {
            if(!isSttRunning){
                mBinding!!.ivRecorderStatus.setImageResource(R.drawable.baseline_record_voice_over_on)
                isSttRunning = true
                startRecording()
            } else{
                mBinding!!.ivRecorderStatus.setImageResource(R.drawable.baseline_record_voice_over_off)
                isSttRunning = false
                stopRecording()
            }
        }
    }

    private fun startRecording(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext)
        speechRecognizer.setRecognitionListener(mListener)
        speechRecognizer.startListening(mIntent)
    }

    private fun stopRecording(){
        speechRecognizer.stopListening()
        mBinding!!.tvSttResult.text = ""
    }
}