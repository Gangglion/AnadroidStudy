package com.glion.woof.data

import com.glion.woof.R
import com.glion.woof.model.Dog

object DataSource {
    fun loadDogData(): List<Dog>{
        return listOf(
            Dog(R.drawable.koda, R.string.koda, 2, R.string.dog_hobby_1),
            Dog(R.drawable.lola, R.string.lola, 16, R.string.dog_hobby_2),
            Dog(R.drawable.frankie, R.string.frankie, 2, R.string.dog_hobby_3),
            Dog(R.drawable.nox, R.string.nox, 8, R.string.dog_hobby_4),
            Dog(R.drawable.faye, R.string.faye, 8, R.string.dog_hobby_5),
            Dog(R.drawable.bella, R.string.bella, 14, R.string.dog_hobby_6),
            Dog(R.drawable.moana, R.string.moana, 2, R.string.dog_hobby_7),
            Dog(R.drawable.tzeitel, R.string.tzeitel, 7, R.string.dog_hobby_8),
            Dog(R.drawable.leroy, R.string.leroy, 4, R.string.dog_hobby_9),
        )
    }
}