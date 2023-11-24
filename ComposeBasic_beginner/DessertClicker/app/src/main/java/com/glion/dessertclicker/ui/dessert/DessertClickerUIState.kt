package com.glion.dessertclicker.ui.dessert

import com.glion.dessertclicker.data.Datasource.dessertList
import com.glion.dessertclicker.model.Dessert

data class DessertClickerUIState(
    val currentIndex: Int = 0,
    val revenue: Int = 0,
    val dessertSold: Int = 0,
    val currentDessert: Dessert = dessertList[currentIndex]
)