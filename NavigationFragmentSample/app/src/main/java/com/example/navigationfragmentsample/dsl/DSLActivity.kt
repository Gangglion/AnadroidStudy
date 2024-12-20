package com.example.navigationfragmentsample.dsl

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.navigationfragmentsample.R
import com.example.navigationfragmentsample.dsl.data.DslResultData
import com.example.navigationfragmentsample.dsl.data.ResultDataParametersType
import com.example.navigationfragmentsample.databinding.ActivityDslactivityBinding
import com.example.navigationfragmentsample.dsl.route.AppRoute
import kotlin.reflect.typeOf

/**
 * Kotlin DSL 을 사용하여 Programmatically 하게 Fragment 전환
 * DSL : Domain Specific Language dml 약자로 특정 도메인에 대한 목적으로 만들어진 언어를 의미, Kotlin 으로 작성된 도메인 특화 언어
 */
class DSLActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDslactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dslactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostDsl.id) as NavHostFragment
        val navController = navHostFragment.navController
        // note : nav_graph 를 컴파일 시 아래처럼 생성한다(compose 의 navigation 과 유사한 느낌)
        navController.graph = navController.createGraph(
            startDestination = AppRoute.Home
        ) {
            fragment<FragmentDslHome, AppRoute.Home> {
                label = AppRoute.Home.route
            }
            fragment<FragmentDslOption1, AppRoute.Option1> {
                label = AppRoute.Option1.route
            }
            fragment<FragmentDslOption2, AppRoute.Option2> {
                label = AppRoute.Option2.route
            }
            fragment<FragmentDslOption2_1, AppRoute.Option2_1> {
                label = AppRoute.Option2_1.route
            }
            fragment<FragmentDslResult, AppRoute.Result>(
                // 인자 전달 - 원시타입이 아닌 데이터의 경우, 아래의 방식으로 전달한다.
                // DslResultData 의 각 property 를 map 형태로 저장하여 전달하게 되는데, 전달을 위해 DslResultData(일반 Data class) 를 NavType<DslResultData> 로 변환한다.
                // 따라서, 전달을 위해선 전달할 data class(@Serialize, @Parcelize 선언된) 와 이를 NavType 으로 만들어 주는 코드 2가지가 필요하다 - DslResultData 참고
                typeMap = mapOf(typeOf<DslResultData>() to ResultDataParametersType)
            ) {
                label = AppRoute.Result.route
            }
            fragment<FragmentDslFind, AppRoute.Find> {
                label = AppRoute.Find.route
            }
        }
    }
}