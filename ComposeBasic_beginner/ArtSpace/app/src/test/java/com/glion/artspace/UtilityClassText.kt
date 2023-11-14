package com.glion.artspace

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilityClassText {
    @Test
    fun getImageResourceTest(){
        val utility = Utility()
        val expectResult = listOf(R.drawable.iron_man_1, R.drawable.iron_man_2, R.drawable.iron_man_3, R.drawable.avengers)
        for(step in 0..3){
            val actualResult = utility.getImageResource(step)
            assertEquals(expectResult[step], actualResult)
        }
    }

    @Test
    fun getStringResourceTest(){
        val utility = Utility()
        val expectResult = listOf(
            Info(R.string.title_1, R.string.artist_1, R.string.year1),
            Info(R.string.title_2, R.string.artist_2, R.string.year2),
            Info(R.string.title_3, R.string.artist_3, R.string.year3),
            Info(R.string.title_4, R.string.artist_4, R.string.year4),
        )
        for(step in 0..3){
            val actualResult = utility.getStringResources(step)
            assertEquals(expectResult[step], actualResult)
        }
    }
}