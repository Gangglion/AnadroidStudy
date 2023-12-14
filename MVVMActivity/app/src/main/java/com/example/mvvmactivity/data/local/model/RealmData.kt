package com.example.mvvmactivity.data.local.model

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class RealmData(
    @PrimaryKey
    var index: Int? = null,
    @Required
    var title: String? = null
) : RealmObject()