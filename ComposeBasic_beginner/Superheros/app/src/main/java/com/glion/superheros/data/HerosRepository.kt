package com.glion.superheros.data

import com.glion.superheros.R
import com.glion.superheros.model.SolHero

object HerosRepository {
    val heros = listOf(
        SolHero(
            nameRes = R.string.hero1,
            descriptionRes = R.string.description1,
            imageRes = R.drawable.superhero1
        ),
        SolHero(
            nameRes = R.string.hero2,
            descriptionRes = R.string.description2,
            imageRes = R.drawable.superhero2
        ),
        SolHero(
            nameRes = R.string.hero3,
            descriptionRes = R.string.description3,
            imageRes = R.drawable.superhero3
        ),
        SolHero(
            nameRes = R.string.hero4,
            descriptionRes = R.string.description4,
            imageRes = R.drawable.superhero4
        ),
        SolHero(
            nameRes = R.string.hero5,
            descriptionRes = R.string.description5,
            imageRes = R.drawable.superhero5
        ),
        SolHero(
            nameRes = R.string.hero6,
            descriptionRes = R.string.description6,
            imageRes = R.drawable.superhero6
        ),
    )
}