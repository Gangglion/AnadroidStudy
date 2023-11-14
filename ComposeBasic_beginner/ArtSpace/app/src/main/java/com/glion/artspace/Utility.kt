package com.glion.artspace

// MEMO : MainActivity의 ArtSpaceLayout의 step 에 따라 다른 resource 리턴해주는 함수 모음
class Utility {
    fun getImageResource(step: Int): Int{
        return when(step){
            0 -> R.drawable.iron_man_1
            1 -> R.drawable.iron_man_2
            2 -> R.drawable.iron_man_3
            else -> R.drawable.avengers
        }
    }
    fun getStringResources(step: Int): Info{
        return when(step){
            0 ->{
                Info(R.string.title_1, R.string.artist_1, R.string.year1)
            }
            1 ->{
                Info(R.string.title_2, R.string.artist_2, R.string.year2)
            }
            2->{
                Info(R.string.title_3, R.string.artist_3, R.string.year3)
            }
            else ->{
                Info(R.string.title_4, R.string.artist_4, R.string.year4)
            }
        }
    }
}