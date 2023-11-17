package com.glion.superheros.data

import com.glion.superheros.R
import com.glion.superheros.model.Hero

object DataSource {
    fun loadHeroDatas(): List<Hero>{
        return listOf(
            Hero(R.drawable.superhero1, R.string.hero1, R.string.description1),
            Hero(R.drawable.superhero2, R.string.hero2, R.string.description2),
            Hero(R.drawable.superhero3, R.string.hero3, R.string.description3),
            Hero(R.drawable.superhero4, R.string.hero4, R.string.description4),
            Hero(R.drawable.superhero5, R.string.hero5, R.string.description5),
            Hero(R.drawable.superhero6, R.string.hero6, R.string.description6)
        )
    }
}