package com.glion.woof.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Dog(
    @DrawableRes val dogImageRes: Int,
    @StringRes val dogNameRes: Int,
    val dogAge: Int,
    @StringRes val dogHobby: Int
)
