package com.example.mvvmactivity.data.local.model

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class TempData: RealmModel {
    @PrimaryKey
    var index: Int? = null
    var title: String? = null
}