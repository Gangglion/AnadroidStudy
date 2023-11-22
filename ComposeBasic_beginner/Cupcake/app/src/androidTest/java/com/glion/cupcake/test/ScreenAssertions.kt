package com.glion.cupcake.test

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

// MEMO : NavController 의 대상경로가 예상과 같은지 테스트하는 도우미 함수
// NavController 의 확장 함수
fun NavController.assertCurrentRouteName(expectedRouteName: String){
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}