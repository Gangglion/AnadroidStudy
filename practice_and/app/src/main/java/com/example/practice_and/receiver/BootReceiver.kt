package com.example.practice_and.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.practice_and.common.RealmClass
import com.example.practice_and.common.toTimestamp
import com.example.practice_and.data.StepPref
import com.example.practice_and.service.StepService
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.S)
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals("android.intent.action.BOOT_COMPLETED")) {
            context?.startService(Intent(context, StepService::class.java))
            if(RealmClass.findTargetDate(LocalDateTime.now().toString()).isEmpty()){ // 전원을 껏다 켯는데 오늘 날짜가 생성 안되어있을 경우
                RealmClass.createTodayField(LocalDateTime.now().toString(), LocalDateTime.now().toTimestamp()) // 날짜 Realm 생성
                StepPref.step = 0 // 걸음수 초기화
            }
        }
    }
}