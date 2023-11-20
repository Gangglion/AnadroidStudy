package com.glion.superheros.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SolHero(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)
