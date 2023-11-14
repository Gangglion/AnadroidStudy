package com.glion.artspace

import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.glion.artspace.ui.theme.ArtSpaceTheme

import org.junit.Test

import org.junit.Rule

class ArtspaceUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun nextButtonClickTest(){
        composeTestRule.setContent{
            ArtSpaceTheme {
                ArtSpaceLayout()
            }
        }
        for(count in 0..15){ // MEMO : 총 16번 클릭하는 테스트
            composeTestRule.onNodeWithText("Next").performClick()
            val actualImage = composeTestRule.onNodeWithTag("posterImg")
            val actualTitle = composeTestRule.onNodeWithTag("title")
            val actualContent = composeTestRule.onNodeWithTag("artist")
            actualImage.assertExists("No Image")
            actualTitle.assertExists("No Title")
            actualContent.assertExists("No Artist")
        }

        for(count in 0..15){ // MEMO : 총 16번 클릭하는 테스트
            composeTestRule.onNodeWithText("Previous").performClick()
            val actualImage = composeTestRule.onNodeWithTag("posterImg")
            val actualTitle = composeTestRule.onNodeWithTag("title")
            val actualContent = composeTestRule.onNodeWithTag("artist")
            actualImage.assertExists("No Image")
            actualTitle.assertExists("No Title")
            actualContent.assertExists("No Artist")
        }
    }

    // TODO : 클릭했을때 원하는 이미지와 텍스트가 잘 나왔는지 확인하려면 어떻게 해야할까..?
}