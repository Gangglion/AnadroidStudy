package com.example.navigationfragmentsample.dsl.route

import com.example.navigationfragmentsample.dsl.data.DslResultData
import kotlinx.serialization.Serializable

// note : Kotlin DSL 을 사용하여 Fragment 를 사용할 경우 각 경로를 고유한 유형으로 표시한다. 인수 처리는 parameter 에 들어간다.
//  XML 기반 네비게이션 그래프는 Android 빌드 시 그래프에 정의된 Destination 에 대한 ID 가 생성된다.
//  Navigation DSL 은 네비게이션 그래프를 Programmatically 한 방식으로 생성하기 때문에 런타임에 생성된다. 따라서 빌드 시 생성되는 Destination 에 대한 Id 를 사용할 수 없으므로, ID 대신 Serialize 가능한 유형을 사용한다.
//  (그렇기 때문에 Navigation DSL 방식은 nav_graph 가 필요없다.)
//  이를 위해, build.gradle 에서 "org.jetbrains.kotlinx:kotlinx-serialization-json:$latest" 를 추가하여 사용해야 한다.
@Serializable
sealed class AppRoute(val label: String) {
    @Serializable data object Home : AppRoute(label = "home")
    @Serializable data class Option1(val sendValue: Int) : AppRoute(Companion.label) {
        companion object {
            const val label = "option1"
        }
    }
    @Serializable data object Option2 : AppRoute(label = "option2")
    @Serializable data object Option2_1 : AppRoute(label = "option2_1")
    // note : Route 의 인수는 직렬화 가능해야 한다. primitive 타입일 경우 직렬화가 바로 가능하지만, List 이거나 Custom Class 인 경우 NavType 을 별도로 생성해주어야 한다. DslResultData.kt 참고
    @Serializable data class Result(val resultData: DslResultData) : AppRoute(Companion.label) {
        companion object {
            const val label = "result"
        }
    }
    @Serializable data object Find : AppRoute(label = "find")
}
