package com.glion.dessertclicker.ui.dessert

import androidx.annotation.DrawableRes
import com.glion.dessertclicker.R
import com.glion.dessertclicker.data.Datasource
import com.glion.dessertclicker.data.Datasource.dessertList
import com.glion.dessertclicker.model.Dessert

data class DessertClickerUIState(
    val dessertIndex: Int = 0,
    val revenue: Int = 0,
    val dessertSold: Int = 0,
    @DrawableRes val imageRes: Int = dessertList[dessertIndex].imageId,
    val currentPrice: Int = dessertList[dessertIndex].price
)