package com.glion.superheros.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @DrawableRes val heroProfile: Int,
    @StringRes val heroTitle: Int,
    @StringRes val heroContent: Int
)
