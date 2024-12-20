package com.example.navigationfragmentsample.dsl.data

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class DslResultData(
    val title: String,
    val value: Int
) : Parcelable

// Kotlin DSL 을 사용하여 Fragment 간 Object 전달을 위해, 전달하고자 하는 데이터 타입을 NavType 으로 만들어 전달해야 한다.
val ResultDataParametersType = object : NavType<DslResultData>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): DslResultData? {
        return bundle.getParcelable(key) as DslResultData?
    }

    override fun parseValue(value: String): DslResultData {
        return Json.decodeFromString<DslResultData>(value)
    }

    override fun put(bundle: Bundle, key: String, value: DslResultData) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: DslResultData): String {
        return Json.encodeToString(DslResultData.serializer(), value)
    }
}