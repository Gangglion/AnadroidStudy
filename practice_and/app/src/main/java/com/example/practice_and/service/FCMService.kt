package com.example.practice_and.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.practice_and.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    // 클라우드 서버에서 메시지를 전송하면 자동으로 호출됨. 여기서 받은 메시지로 사용자에게 알람 보내는 등의 행동을 할 수 있음
    override fun onMessageReceived(message: RemoteMessage) { // RemoteMessage : 수신된 메시지 전달됨
        super.onMessageReceived(message)
        fcmCreateNoti(message.notification!!.title, message.notification!!.body)
    }

    // 새로운 토큰이 생성될 때마다 호출됨
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("tmdguq", "new Token Published : $token")
    }

    @SuppressLint("MissingPermission")
    private fun fcmCreateNoti(title: String?, content: String?){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channelId = getString(R.string.default_fcm_channelId)
            val notiChannel = NotificationChannel(
                channelId,
                "FCM NOTI",
                NotificationManager.IMPORTANCE_HIGH
            ).apply{
                setShowBadge(true)
                description = "FCM NOTI TEST"
            }
            val notiManager = NotificationManagerCompat.from(application)
            notiManager.createNotificationChannel(notiChannel)

            val builder = NotificationCompat.Builder(application, channelId)
            builder.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.baseline_cloud_24)

            val notification = builder.build()
            notiManager.notify(2, notification)
        }
    }
}