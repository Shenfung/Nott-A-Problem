package com.example.nott_a_problem

import androidx.navigation.NavController

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.nott_a_problem.navigation.AppNavigation
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigate_to_login_screen_from_home() {

        val mockNavController = mockk<NavController>(relaxed = true)

        composeTestRule.setContent {
            AppNavigation()
        }

        composeTestRule.onNodeWithText("Home").assertExists()

        composeTestRule.onNodeWithText("Go to Login").performClick()

        verify { mockNavController.navigate("login") }
    }
}
