package com.example.navigationfragmentsample.graph.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultData(
    val title: String,
    val value: Int
) : Parcelable

// note : 아래는 kotlin 의 Parcelize 어노테이션을 추가함으로서 자동으로 생성된다.
//  build.gradle 의 "id("kotlin-parcelize")" 을 추가해줌으로서 사용이 가능하고, 이를 사용하지 않을 시 아래처럼 직접 생성해주어야 한다.
//  다만 이러한 방식때문에 Parcelize 는 Serialize 보다 속도가 매우 빠르다(reflection 코드가 생성되고, GC 에 의해 수집되는 과정이 생략되기 때문)
/*
 {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultData> {
        override fun createFromParcel(parcel: Parcel): ResultData {
            return ResultData(parcel)
        }

        override fun newArray(size: Int): Array<ResultData?> {
            return arrayOfNulls(size)
        }
    }
}
 */