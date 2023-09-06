package com.example.practice_and.common

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun LocalDateTime.toString(format: String): String = this.format(DateTimeFormatter.ofPattern(format))

// LocalDateTime으로 변환하는건 포멧이 맞아야함.
fun LocalDateTime.toDate() : LocalDateTime = LocalDateTime.parse(this.toString("yyyy-MM-dd HH:mm:ss.SSS"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))

fun LocalDateTime.toTimestamp(date: LocalDateTime? = null): Long{
    if(date!=null){
        return Timestamp.valueOf(date.toString().replace("T", " ")).time
    } else{
        return Timestamp.valueOf(this.toString().replace("T", " ")).time
    }
}

fun LocalDateTime.getDayOfWeekKO(): String{
    return when(this.dayOfWeek.value){
        1->{"월요일"}
        2->{"화요일"}
        3->{"수요일"}
        4->{"목요일"}
        5->{"금요일"}
        6->{"토요일"}
        7->{"일요일"}
        else->{"None"}
    }
}

fun LocalDateTime.getCalDay(day: Long): LocalDateTime{
    return this.plusDays(day)
}

fun LocalDateTime.getCalMin(day: Long): LocalDateTime{
    return this.plusMinutes(day)
}