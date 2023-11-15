package com.glion.basickotlin


// MEMO : 람다 표현식의 매개변수 유형, 반환 유형
//  () -> Unit : 매개변수가 없고, 반환값이 없다.
//  (String) -> String : String 형 매개변수에 반환형식은 String 이다.
//  와 같이 쓴다. (..) 안이 매개변수, -> 뒤에가 반환형식이다.
//  변수에 참조할때는 변수의 타입을 지정하는 것처럼 : 뒤에 써준다.
//  함수의 리턴값이 람다 표현식으로 하려면 fun 함수명(매개변수): () -> Unit {...} 과 같이 써준다.
//  매개변수를 취하는 함수에 대한 람다표현식은 "val 함수명 = { arg1, arg2 -> ... }" 와 같이 사용한다.
object Lamda {
    fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int)->String)?): () -> Unit{
        return if(isTrick) trick
        else {
            if(extraTreat != null){
                println(extraTreat(5))
            }
            treat
        }
    }
    val trick = { // MEMO : 람다 표현식
        println("No treats!")
    }

    val treat: () -> Unit = {
        println("Have a treat!")
    }
}