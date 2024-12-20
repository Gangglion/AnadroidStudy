package com.example.navigationfragmentsample.dsl.route

import androidx.annotation.AnimRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.navigationfragmentsample.R

/**
 * 공통 애니메이션을 적용시키기 위한 NavController 확장함수
 * 이렇게 하지 않으면 navigate 할때마다 navOptions 객체를 생성하여 navigate 에 전달해주어야 하니, 보일러플레이트 코드를 줄이기 위해 다음과 같은 확장함수를 사용함
 */
fun NavController.navigateWithAnim(
    route: AppRoute,
    isInclusive: Boolean = false,
    @AnimRes enterAnim: Int = R.anim.slide_in,
    @AnimRes exitAnim: Int = androidx.navigation.ui.R.anim.nav_default_exit_anim,
    @AnimRes popEnterAnim: Int = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim,
    @AnimRes popExitAnim: Int = R.anim.slide_out
) {
    val naviOptions = NavOptions.Builder()
        .setEnterAnim(enterAnim)
        .setExitAnim(exitAnim)
        .setPopEnterAnim(popEnterAnim)
        .setPopExitAnim(popExitAnim)
    // 지정한 경로까지 이동할때 백스택에 남아있는 화면을 함꼐 지울지 여부를 체크
    //  3 depth 까지 있는 화면이 있을때, 3depth 에서 1 depth 로 이동하고 싶을때 이 옵션을 적용하지 않으면 1 depth 로 갔다가 navigateUp 을 했을때 다시 3depth 로 이동할 수 있다.
    if(isInclusive) {
        naviOptions.setPopUpTo(route, true)
    }

    navigate(route, naviOptions.build())
}