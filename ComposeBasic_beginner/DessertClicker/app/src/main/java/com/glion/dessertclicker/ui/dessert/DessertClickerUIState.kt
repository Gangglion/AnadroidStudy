package com.glion.dessertclicker.ui.dessert

import com.glion.dessertclicker.R
import com.glion.dessertclicker.model.Dessert

data class DessertClickerUIState(
    val revenue: Int = 0,
    val dessertSold: Int = 0,
    val currentDessert: Dessert = Dessert(R.drawable.cupcake, 5, 0)
)