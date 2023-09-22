package com.example.practice_and.pedometer_and_chart

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.toString
import com.example.practice_and.toTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class StepService : Service(), SensorEventListener {
    companion object {
        const val CHANNEL_ID = "1"
        const val CHANNEL_NAME = "StepNoti"
        const val CHANNEL_DESCRIPTION = "StepNoti TestProj"
        const val NOTIFICATION_BUILDER_ID = 1
    }

    private lateinit var mContext: Context

    private lateinit var mSensorManager: SensorManager
    private var mSensor: Sensor? = null

    private lateinit var mNotificationCompatBuilder: NotificationCompat.Builder
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotificationChannel: NotificationChannel

    private var mOriginStep: Int = 0
    private var isFirst: Boolean = true

    // 날짜 바뀌면 호출되는 리시버 서비스 내부에 호출
    inner class DayChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == Intent.ACTION_DATE_CHANGED){
                // 날짜 바뀌면 UserData 초기화
                StepPref.step = 0

                // 걸음수 초기화와 동시에 테이블 생성 - 생성하려는 날짜와 동일한 데이터가 이미 들어가 있는지 확인해볼 필요 있음
                val data = RealmClass.findTargetDate(LocalDateTime.now().toString()) // 날짜가 바뀌었을때, 오늘 날짜에 해당하는 데이터가 가져와지면 그냥 넘어간다
                if(data.isEmpty()){
                    RealmClass.createTodayField(LocalDateTime.now().toString(), LocalDateTime.now().toTimestamp())
                }

                // 일주일전 데이터 일괄 삭제
                RealmClass.deleteData()
                val updateNotification = updateNotification(StepPref.status, StepPref.step)
                mNotificationManager.notify(NOTIFICATION_BUILDER_ID, updateNotification) // 상단바 알림 업데이트
                sendDateChangeToActivity(mContext)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("tmdguq", "Service onCreate()")
        mContext = this

        // about Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_NONE
                ).apply {
                    description = CHANNEL_DESCRIPTION
                    setShowBadge(false)
                }
            mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            mNotificationManager.createNotificationChannel(mNotificationChannel)
        }
        // sensor initialize
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tmdguq", "service - onDestroy")
        mSensorManager.unregisterListener(this, mSensor) // 센서 리스너 해제
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("tmdguq", "Service onStartCommnad()")

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST)

        val notificationBuild = createNotification()
        startForeground(1, notificationBuild) // 알림을 포그라운드에 등록시켜 서비스가 종료되지 않게 함

        return START_STICKY
        // START_NOT_STICKY : 시스템에서 해당 서비스 강제종료 할 경우 이후 서비스가 재생성되지 않는다. 필요하지 않은 서비스가 계속 작동되는 것을 막을 수 있음
        // START_STICKY : 시스템에서 해당 서비스를 강제종료 할 경우 서비스를 호출하는 intent를 null로 하여 호출해준다
        // START_REDELIVER_INTENT : 시스템에서 서비스를 강제 종료 할 경우 서비스를 재생성 해주고 onStartCommand를 호출하게 한다.
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun onSensorChanged(event: SensorEvent?) {
//        var mStep = StepPref.step
//        if(event?.sensor?.type == Sensor.TYPE_STEP_COUNTER){
//            if(isFirst){
//                mOriginStep = event.values[0].toInt()
//                isFirst = false
//            } else{
//                val unitStep = event.values[0].toInt() - mOriginStep
//                mStep += unitStep
//                mOriginStep = event.values[0].toInt()
//                StepPref.step = mStep
//            }
//            val notificationBuild = updateNotification(StepPref.status, StepPref.step)
//            mNotificationManager.notify(NOTIFICATION_BUILDER_ID, notificationBuild) // 상단바 알림 업데이트
//        } else{
//            Log.d(App.TAG, "sensorEvent null")
//        }
//        RealmClass.updateStep(LocalDateTime.now().toString(App.DATE_FORMAT), StepPref.step)
//        // sendStepDataToActivity(mContext)
//
//        val notificationBuild = updateNotification(
//            StepPref.status,
//            StepPref.step,
//        )
//        mNotificationManager.notify(1, notificationBuild) // 알림 내용 업데이트
        if(event!!.sensor!!.type == Sensor.TYPE_STEP_COUNTER){
            val sensorValue = event.values[0].toInt()
            processSensorData(sensorValue)
            val notificationBuild = updateNotification(StepPref.status, StepPref.step)
            mNotificationManager.notify(NOTIFICATION_BUILDER_ID, notificationBuild) // 상단바 알림 업데이트
        } else{
            Log.e("shhan", "SensorEvent is Null!!")
        }
        RealmClass.updateStep(LocalDateTime.now().toString(App.DATE_FORMAT), StepPref.step)

        val notificationBuild = updateNotification(
            StepPref.status,
            StepPref.step,
        )
        mNotificationManager.notify(1, notificationBuild) // 알림 내용 업데이트
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("tmdguq", "onAccuracyChanged Call")
    }

    // coroutines Test
    private val sensorScope = CoroutineScope(Dispatchers.Default)
    private fun processSensorData(countedStep: Int){
        sensorScope.launch {
            // 백그라운드스레드에서 실행되는 코드 부분.
            // 걸음수 처리를 여기서 해주면 될까?
            var mStep = StepPref.step
            if(isFirst){
                mOriginStep = countedStep
                isFirst = false
            } else{
                mStep += (countedStep - mOriginStep)
                StepPref.step = mStep
                mOriginStep = countedStep
            }
            Log.d("shhan", "측정된 걸음수 : $mStep")

            withContext(Dispatchers.Main){
                // 메인 UI 스레드에서 실행되는 부분
                // UI 조작 관련된 걸 함
                sendStepDataToActivity(mContext)
            }
        }
    }

    // notification initialize
    private fun createNotification(): Notification {
        // Notification tab setting
        val intent = Intent(this, StepActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val msg = "센서 상태 : false / 걸음수 : 0"
        mNotificationCompatBuilder = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_directions_walk_24) // 아이콘
            .setWhen(System.currentTimeMillis())
            .setContentTitle("걸음수")
            .setAutoCancel(false) // 알림 클릭시 알림 자동 사라짐
            .setPriority(NotificationCompat.PRIORITY_LOW) // 소리없이 상태바에만 나옴
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true) // true : 알림 사라지지 않음, false : 스와이프로 알림 지워짐
            .setContentIntent(pendingIntent)
            .setContentText(msg)
            .setShowWhen(false) // 사용자의 혼동을 막기 위함

        return mNotificationCompatBuilder.build()
    }

    // notification update
    private fun updateNotification(status: Boolean, step: Int): Notification {
        val msg = "센서 상태 : $status / 걸음수 : $step "
        mNotificationCompatBuilder.setContentText(msg)

        return mNotificationCompatBuilder.build()
    }

    private fun sendStepDataToActivity(context: Context){
        val intent = Intent("step")
        intent.putExtra("value", StepPref.step)
        Log.d(App.TAG, StepPref.step.toString())
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun sendDateChangeToActivity(context: Context){
        val intent = Intent("dayChange")
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}
