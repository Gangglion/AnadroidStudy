package com.glion.woof.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(50.dp), // MEMO : 0을 지정하면 완전 직각, 50을 지정하면 모서리가 완전 둥글어짐. 각 모서리마다 다른 곡률을 주어 다양한 모양을 만들 수 있음
    medium = RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 16.dp) // MEMO : 각 모서리는 topStart -> topEnd -> bottomEnd -> bottomStart 순
)