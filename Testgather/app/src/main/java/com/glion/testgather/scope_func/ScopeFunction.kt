package com.glion.testgather.scope_func

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.glion.testgather.R

class ScopeFunction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope_function)

        val personApply = Person().apply{
            name = "Glion"
            age = 26
        }

        val personCalAge = run{
            val person = Person() // run scope 내에서 객체 생성
            person.name = "Glion"
            person.age = 26
            person.age // 생성한 객체의 멤버변수 리턴
        }
        Log.d("shhan", "personAge : $personCalAge")

        val personCalAge2 = Person().run{
            age = 26 // 수신객체 Person()을 이용하여 age에 접근
            age * 2 // Person의 age값에 *2 하여 리턴
        }
        Log.d("shhan", "personAge * 2 : $personCalAge2")

        val personNameLength = Person().run{ name?.length } ?: 0
        Log.d("shhan", personNameLength.toString())

        val personLet = Person().let{

        }
    }
}