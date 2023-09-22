package com.example.practice_and.pedometer_and_chart

import android.content.Context
import android.util.Log
import com.example.practice_and.App
import com.example.practice_and.getCalDay
import com.example.practice_and.toTimestamp
import io.realm.*
import io.realm.kotlin.where
import java.time.LocalDateTime

object RealmClass {
    var realm: Realm? = null


    /**
     * Realm 초기화
     * @param context Realm 초기화 한 위치의 Context
     */
    fun initialize(context: Context){
        Realm.init(context)
        val realmName = "StepRealm"
        val config = RealmConfiguration.Builder()
            .name(realmName)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(config)
    }

    /**
     * Realm에 저장된 전체 데이터 반환
     */
    fun findAllData() : List<StepData>{
        return realm?.where<StepData>()!!.findAll()
    }
    /**
     * 특정 날짜의 데이터 반환
     * @param targetDate 데이터를 찾을 날짜.
     */
    fun findTargetDate(targetDate: String): List<StepData>{
        return realm?.where<StepData>()!!.equalTo("date", targetDate).findAll()
    }

    /**
     * 특정 날짜와 timestamp값을 넣어 필드 만드는 함수
     * @param date 새로 만들 필드의 기본키
     * @param timestamp 새로 만들 필드의 timestamp
     */
    fun createTodayField(date: String, timestamp: Long){
        val task = StepData()
        task.date = date
        task.dateTimestamp = timestamp
        realm?.executeTransaction {
            it.insert(task)
        }
    }
    /**
     * 특정 날짜의 걸음수 필드 업데이트
     * @param date 업데이트할 필드를 찾기 위한 기본키 날짜
     * @param step 업데이트가 이루어지는 걸음수 값
     */
    fun updateStep(date: String, step: Int){
        try{
            realm?.executeTransaction {
                val task = it.where<StepData>().equalTo("date", date).findFirst()
                task?.step = step
            }
        } catch (e: Exception){
            Log.d(App.TAG, "updateStep error")
        }

    }
    /**
     * 특정 날짜의 실제 걸음 수 리턴하는 함수
     * @param date xxxx. xx. xx 형태로 된 날짜 문자열
     */
    fun getDateDidStep(date: String): Int{
        var task = StepData()
        realm?.executeTransaction {
            task = it.where<StepData>().equalTo("date", date).findFirst()!!
        }
        return task.step
    }

    /**
     * 일주일 지난 데이터 삭제 하는 함수
     */
    fun deleteData(){
        val targetDate = LocalDateTime.now().getCalDay(6)
        val targetTimestamp = LocalDateTime.now().toTimestamp(targetDate)
        try{
            realm?.executeTransaction {
                val task = it.where<StepData>().lessThan("dateTimestamp", targetTimestamp).findAll()
                task.deleteAllFromRealm()
            }
        } catch(e:Exception){
            Log.w(App.TAG, "Realm 삭제 에러남")
        }
    }
}