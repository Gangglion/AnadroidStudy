package com.example.practice_and.data

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class StepData: RealmObject() {
    var date: String = ""
    var dateTimestamp: Long = 0L
    var step: Int = 0
}