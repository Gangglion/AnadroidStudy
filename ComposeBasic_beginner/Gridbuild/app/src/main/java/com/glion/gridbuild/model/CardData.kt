package com.glion.gridbuild.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
data class GridRowData(
    val cardData1: CardData,
    val cardData2: CardData
)
data class CardData(
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int,
    val count: Int
)