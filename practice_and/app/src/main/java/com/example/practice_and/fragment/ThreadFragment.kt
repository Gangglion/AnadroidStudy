package com.example.practice_and.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import com.example.practice_and.R
import com.example.practice_and.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlin.concurrent.thread

class ThreadFragment : Fragment() {

    inner class Runnable1() : Runnable{
        override fun run() {
            for(i in 0..10){
                Log.d("shhan", "${Thread.currentThread().name} $i")
            }
        }
    }

    inner class Thread2 : Thread(){
        override fun run() {
            val th1 : Thread = Thread(Runnable1()).apply{
                name = "스레드 1"
            }
            th1.start()
            th1.join()
            Log.d("shhan", "Thread1 is End after Thread2 Start")
            for(i in 10..20){
                Log.d("shhan", "${this.name} Run $i")
            }
        }
    }

    inner class ExThread(seq: Int) : Thread(){
        private val seq: Int
        init{
            this.seq = seq
        }
        override fun run() {
            Log.i("shhan", "$seq 번째 스레드 시작")
            try{
                sleep(1000)
            } catch(e: Exception){
                e.printStackTrace()
            }
            Log.w("shhan", "$seq 번째 스레드 종료")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_thread, container, false)

        // MEMO : 스레드 1 실행 이후, 끝나면 스레드 2 실행하게끔 되어있다. 메인 스레드는 계속 돌다가 스레드 2가 끝나면 종료한다.
        root.findViewById<AppCompatButton>(R.id.btn_start_thread).apply{
            setOnClickListener {
                Log.d("shhan", "Thread Priority : ${Thread.currentThread().priority}")
                val th2 = Thread2().apply{
                    name = "스레드 2"
                }
                th2.start()
                while(true){
                    if(!th2.isAlive){
                        Log.d("shhan", "Thread2 is dead, while loop end")
                        break
                    }
                    Log.d("shhan", "Current Thread : ${Thread.currentThread()}")
                }
            }
        }

        // MEMO : 스레드 특징 파악
        //  1. 스레드는 생성한 순서대로 실행되지 않는다. 생성 순서대로 실행시키고 싶다면 join()을 이용한다.(join은 해당 스레드가 끝날때까지 join을 호출한 스레드의 실행을 멈춘다)
        //      * priority를 사용해서 우선순위를 정해주면 순서대로 실행되지 않을까? -> 우선순위 설정은 추천 순위 정도이지, 우선순위를 높게 설정한다고 항상 먼저 실행되진 않는다.
        //  2. 스레드의 종료는 실행된 순서와 무관하다.
        //  3. 메인메소드(클릭리스너) 의 종료는 스레드의 종료와 무관하게 동작한다.(스레드의 종료에 영향을 받지 않는다)
        root.findViewById<AppCompatButton>(R.id.btn_start_thread2).apply{
            setOnClickListener{
                for(i in 1..10){
                    val exThread = ExThread(i)
                    exThread.start()
                }
                Log.d("shhan", "Main Thread End")
            }
        }

        // MEMO : 동일한 동작을 코루틴으로 구현해보자.
        root.findViewById<AppCompatButton>(R.id.btn_start_coroutines).apply{
            setOnClickListener {
                val task1 = GlobalScope.async{
                    for(i in 0..10){
                        Log.d("shhan", "${Thread.currentThread()} : $i")
                    }
                }
                val task2 = GlobalScope.launch {
                    for(i in 11..20){
                        Log.d("shhan", "${Thread.currentThread()} : $i")
                    }
                }
                GlobalScope.launch(Dispatchers.Main){
                    task1.join()
                    Log.d("shhan", "task1 is End")
                }
            }
        }

        return root
    }
}