package com.example.practice_and.pedometer_and_chart

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object AboutDateTimeMethod {
    private var localTime : LocalTime? = null
    private var localDate : LocalDate? = null
    private var localDateTime : LocalDateTime? = null
    private var strLocalDateTime = ""

    fun returnNowDateStr(): String? {
        localDate = LocalDate.now()
        localTime = LocalTime.now()
        localDateTime = LocalDateTime.of(localDate, localTime)

        val myPattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return localDateTime?.format(myPattern)
    }

    fun returnNowDateTimestamp(): Long {
        localDate = LocalDate.now()
        localTime = LocalTime.now()
        localDateTime = LocalDateTime.of(localDate, localTime)
        strLocalDateTime = localDateTime.toString().replace("T", " ")

        return Timestamp.valueOf(strLocalDateTime).time
    }

    // type : 년(y) 월(M) 일(d) 시(H) 분(m) 초(s), value : 빼고싶은 값, return : Timestamp 형태 Long
    fun getBeforeDateTime(type: Char, value: Long): Long{
        when(type){
            'd'->{ // 일
                localDate = LocalDate.now()
                localTime = LocalTime.now()
                val calLocalDate = localDate?.minusDays(value)
                localDateTime = LocalDateTime.of(calLocalDate, localTime)
            }
            'm'->{ // 분
                localDate = LocalDate.now()
                localTime = LocalTime.now()
                val calLocalTime = localTime?.minusMinutes(value)
                localDateTime = LocalDateTime.of(localDate, calLocalTime)
            }
            's'->{ // 초
                localDate = LocalDate.now()
                localTime = LocalTime.now()
                val calLocalTime = localTime?.minusSeconds(value)
                localDateTime = LocalDateTime.of(localDate, calLocalTime)
            }
        }
        strLocalDateTime = localDateTime.toString().replace("T"," ")
        return Timestamp.valueOf(strLocalDateTime).time
    }

}