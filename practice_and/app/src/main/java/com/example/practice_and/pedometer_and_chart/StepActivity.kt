package com.example.practice_and.pedometer_and_chart

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.toString
import com.example.practice_and.toTimestamp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class StepActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private val requiredPermissions = arrayOf(
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    private var mContext: Context? = null

    private var mTvStatus: TextView? = null
    private var mTvStep: TextView? = null
    private var mTvTimeVal: TextView? = null
    private var mTvRecentChart: TextView? = null
    private var isAllGranted = false
    private var mDeniedPermission: ArrayList<String>? = null

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_next -> {
                Log.d("tmdguq", "MPAndroidChart로 이동해야함")
                val intent = Intent(mContext!!, ChartActivity::class.java)
                otherActivityForResult.launch(intent)
            }
            R.id.btn_start_sevice ->{
                if (isAllGranted) {
                    // prevent service duplicated running
                    Log.d("tmdguq", "onResume isAllGranted true")
                    if (!isServiceRunning(mContext!!, StepService::class.java)) {
                        Log.d(
                            "tmdguq",
                            "service status : ${isServiceRunning(mContext!!, StepService::class.java)}"
                        )
                        val intent = Intent(mContext!!, StepService::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mContext!!.startForegroundService(intent)
                        } else {
                            mContext!!.startService(intent)
                        }
                    } else {
                        // service is running
                        Log.d("tmdguq", "service is already running")
                    }
                    LocalBroadcastManager.getInstance(mContext!!)
                        .registerReceiver(mStepServiceReceiver, IntentFilter("step"))
                }
            }
            R.id.btn_stop_service ->{
                if (isServiceRunning(mContext!!, StepService::class.java)) {
                    val intent = Intent(mContext!!, StepService::class.java)
                    stopService(intent)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tmdguq", "onCreate Call")
        setContentView(R.layout.activity_step)

        mContext = this

        mTvStatus = findViewById(R.id.tv_statusVal)
        mTvStep = findViewById(R.id.tv_stepVal)
        mTvTimeVal = findViewById(R.id.tv_timeVal)
        mTvRecentChart = findViewById(R.id.tv_recentchartVal)

        mDeniedPermission = ArrayList()

        findViewById<TextView>(R.id.btn_next).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_start_sevice).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_stop_service).setOnClickListener(this)


        if (StepPref.step != 0) {
            mTvStatus?.text = StepPref.status.toString()
            mTvStep?.text = StepPref.step.toString()
            mTvTimeVal?.text = AboutDateTimeMethod.returnNowDateStr()
        } else {
            mTvStatus?.text = "false"
            mTvStep?.text = "0"
            mTvTimeVal?.text = AboutDateTimeMethod.returnNowDateStr()
        }
        // get FCM Token
        getFCMToken()

        if(RealmClass.findTargetDate(LocalDateTime.now().toString(App.DATE_FORMAT)).isEmpty()){
            RealmClass.createTodayField(LocalDateTime.now().toString(App.DATE_FORMAT), LocalDateTime.now().toTimestamp()) // 날짜에 해당하는 데이터 생성
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("tmdguq", "onStart Call")
        requestPermission()
        // 베터리 최적화 제한 알림
        goBatteryOptimizations()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Log.d(App.TAG, RealmClass.findAllData().toString())
    }

    override fun onPause() {
        super.onPause()
        Log.d("tmdguq", "onPause Call")
    }

    // 서비스 중복 실행 방지 함수
    private fun isServiceRunning(context: Context, serviceName: Class<StepService>): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (runningServiceInfo in am.getRunningServices(50)) {
            if (serviceName.name.equals(runningServiceInfo.service.className)) {
                return true
            }
        }
        return false
    }

    // 권한 부여 여부 확인 - 설정 안되있으면 요청
    private fun requestPermission() {
        Log.d("tmdguq", "fun checkPermission Run")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if ((ContextCompat.checkSelfPermission(
                    mContext!!,
                    requiredPermissions[0]
                ) == PackageManager.PERMISSION_GRANTED)
                && ContextCompat.checkSelfPermission(
                    mContext!!,
                    requiredPermissions[1]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                isAllGranted = true
            }
            ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSION)
            isAllGranted = false
        } else{
            isAllGranted = true
        }
    }

    override fun onRequestPermissionsResult( // 권한 요청 결과 처리 함수
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("tmdguq", "fun onRequestPermissionsResult Call")
        val strBuffer = StringBuffer()
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    isAllGranted = true
                    for (idx in grantResults.indices) {
                        if (grantResults[idx] != PackageManager.PERMISSION_GRANTED) {
                            if (permissions[idx] == getString(R.string.post_permission))
                                strBuffer.append("* 알림\n")
                            if (permissions[idx] == getString(R.string.recognition_permission))
                                strBuffer.append("* 신체 활동\n")
                            isAllGranted = false
                        }
                    }
                    if (isAllGranted) {
                        // 모든 권한 허용됨.
                    } else {
                        // ActivityCompat.shouldShowRequestPermissionRationale : 사용자가 권한 요청에 대해 명시적으로 "거부" 했을 경우 true 반환.
                        // 사용자가 권한 요청에 대해 처음보거나, 다시 묻지 않음 선택한 경우(버전 올라가면서 사라진듯), 권한 허용할 경우 false 반환
                        if ((ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[0]
                            )
                                    || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[1]
                            ))
                        ) {
                            goToSettingDialog(strBuffer).show()
                        } else {
                            goToSettingDialog(strBuffer).show()
                        }
                    }
                }
            }
        }
    }

    private val otherActivityForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                9001 -> { // ChartActivity 에서 오는 결과(resultCode 9001)
                    val intent = result.data
                    val str = intent?.getStringExtra("recent")
                    mTvRecentChart!!.text = str
                }
                Activity.RESULT_CANCELED -> {
                    // 베터리 권한 허용여부 시스템 dialog에서 어떤 버튼을 눌러도 0이 리턴된다. 출력 : 없음(공식문서)
                    // "허용"이나 "거부"를 눌렀을 때 구분이 안되기 때문에 여기서 한번 더 함수 호출하여 거부, 허용 여부를 확인한다.
                    goBatteryOptimizations()
                }
                /*
                처럼 resultCode에 따라 분기처리하여 사용 할 수 있음.
                다른 액티비티로부터 넘겨진 값을 함께 넘어온 resultCode를 통해 분기처리 가능하다.
                다른 액티비티에서 넘길때는 setResult(resultCode, intent) 로 넘길 수 있고, 넘겨주고자 하는 타겟 activity에서는
                해당 콜백에서 resultCode를 기준으로 넘겨받은 데이터에 대한 처리를 할 수 있다.
                */
            }
        }

    private fun goToSettingDialog(strDenied: StringBuffer): AlertDialog {
        val builder = AlertDialog.Builder(mContext!!).apply {
            this.setTitle("권한 요청")
                .setMessage(
                    "권한을 허용해야 앱의 기능을 이용할 수 있습니다.\n설정에서 다음 권한을 허용해 주세요\n" +
                            "${strDenied}\n 확인을 누르면 설정화면으로 이동합니다."
                )
                .setCancelable(false)
            this.setPositiveButton("확인") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent) // 설정으로부터 setResult로 resultCode를 넘겨받지 않으므로 이 부분에서는 startActivity 사용함. 출력에 대한 값이 없음(공식문서)
            }
        }
        return builder.create()
    }

    // get Firebase Cloud Message Token
    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("tmdguq", "Fetching FCM registration token failed", task.exception)

                return@OnCompleteListener
            }
            // Get new FCM registration token
            val getToken = task.result
            Log.d(App.TAG, getToken.toString())
        })
    }

    @SuppressLint("BatteryLife")
    private fun goBatteryOptimizations() {
        val pm = mContext!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val builder = AlertDialog.Builder(mContext!!).apply {
            this.setTitle("베터리 최적화 무시")
                .setMessage(
                    "절전 모드에서도 원활한 걸음수 측정을 위해 베터리 최적화 제한을 해제해주세요.\n거절하게 되면 절전모드에서 걸음수 측정이 안될 수 있습니다."
                )
                .setCancelable(false)
            this.setPositiveButton("확인") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:${mContext!!.packageName}")
                )
                otherActivityForResult.launch(intent)
            }
            this.setNegativeButton("취소") { _, _ ->
                Toast.makeText(mContext!!, "절전모드에서 걸음수 측정이 안될 수 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
        if (!pm.isIgnoringBatteryOptimizations(mContext!!.packageName)) {
            builder.create().show()
        }
    }

    // 걸음수 받아오는 리시버
    private val mStepServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "step") {
                val status = intent.getBooleanExtra("status", false)
                val mStep = intent.getIntExtra("value", 0)

                mTvStatus?.text = status.toString()
                mTvStep?.text = mStep.toString()
            }

        }
    }
}
