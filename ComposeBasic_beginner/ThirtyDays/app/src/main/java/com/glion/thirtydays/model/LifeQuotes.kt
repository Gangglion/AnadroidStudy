package com.glion.thirtydays.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.glion.thirtydays.R

data class LifeQuotes(
    @StringRes val author: Int,
    @StringRes val content: Int,
    @DrawableRes val imageRes: Int
)

val QuotesDataList = listOf(
    LifeQuotes(R.string.author_1, R.string.contents_1, R.drawable.image_1),
    LifeQuotes(R.string.author_2, R.string.contents_2, R.drawable.image_2),
    LifeQuotes(R.string.author_3, R.string.contents_3, R.drawable.image_3),
    LifeQuotes(R.string.author_4, R.string.contents_4, R.drawable.image_4),
    LifeQuotes(R.string.author_5, R.string.contents_5, R.drawable.image_5),
    LifeQuotes(R.string.author_6, R.string.contents_6, R.drawable.image_1),
    LifeQuotes(R.string.author_7, R.string.contents_7, R.drawable.image_2),
    LifeQuotes(R.string.author_8, R.string.contents_8, R.drawable.image_3),
    LifeQuotes(R.string.author_9, R.string.contents_9, R.drawable.image_4),
    LifeQuotes(R.string.author_10, R.string.contents_10, R.drawable.image_5),
    LifeQuotes(R.string.author_11, R.string.contents_11, R.drawable.image_1),
    LifeQuotes(R.string.author_12, R.string.contents_12, R.drawable.image_2),
    LifeQuotes(R.string.author_13, R.string.contents_13, R.drawable.image_3),
    LifeQuotes(R.string.author_14, R.string.contents_14, R.drawable.image_4),
    LifeQuotes(R.string.author_15, R.string.contents_15, R.drawable.image_5),
    LifeQuotes(R.string.author_16, R.string.contents_16, R.drawable.image_1),
    LifeQuotes(R.string.author_17, R.string.contents_17, R.drawable.image_2),
    LifeQuotes(R.string.author_18, R.string.contents_18, R.drawable.image_3),
    LifeQuotes(R.string.author_19, R.string.contents_19, R.drawable.image_4),
    LifeQuotes(R.string.author_20, R.string.contents_20, R.drawable.image_5),
    LifeQuotes(R.string.author_21, R.string.contents_21, R.drawable.image_1),
    LifeQuotes(R.string.author_22, R.string.contents_22, R.drawable.image_2),
    LifeQuotes(R.string.author_23, R.string.contents_23, R.drawable.image_3),
    LifeQuotes(R.string.author_24, R.string.contents_24, R.drawable.image_4),
    LifeQuotes(R.string.author_25, R.string.contents_25, R.drawable.image_5),
    LifeQuotes(R.string.author_26, R.string.contents_26, R.drawable.image_1),
    LifeQuotes(R.string.author_27, R.string.contents_27, R.drawable.image_2),
    LifeQuotes(R.string.author_28, R.string.contents_28, R.drawable.image_3),
    LifeQuotes(R.string.author_29, R.string.contents_29, R.drawable.image_4),
    LifeQuotes(R.string.author_30, R.string.contents_30, R.drawable.image_5)
)
