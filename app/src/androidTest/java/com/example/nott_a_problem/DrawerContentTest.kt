package com.example.nott_a_problem

import com.example.nott_a_problem.dashboard.components.DrawerContent

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class DrawerContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDrawerContent_interactions() {

        val mockOnChangePassword = mockk<() -> Unit>(relaxed = true)
        val mockOnTermsOfService = mockk<() -> Unit>(relaxed = true)
        val mockOnContactUs = mockk<() -> Unit>(relaxed = true)
        val mockOnHelp = mockk<() -> Unit>(relaxed = true)
        val mockOnLogout = mockk<() -> Unit>(relaxed = true)


        composeTestRule.setContent {
            DrawerContent(
                username = "Test User",
                email = "test@example.com",
                onChangePassword = mockOnChangePassword,
                onTermsOfService = mockOnTermsOfService,
                onContactUs = mockOnContactUs,
                onHelp = mockOnHelp,
                onLogout = mockOnLogout
            )
        }

        composeTestRule.onNodeWithText("Test User").assertIsDisplayed()
        composeTestRule.onNodeWithText("test@example.com").assertIsDisplayed()

        composeTestRule.onNodeWithText("Change Password").performClick()
        verify { mockOnChangePassword() }

        composeTestRule.onNodeWithText("Terms Of Service").performClick()
        verify { mockOnTermsOfService() }

        composeTestRule.onNodeWithText("Contact Us").performClick()
        verify { mockOnContactUs() }

        composeTestRule.onNodeWithText("Help\n(Security Office)").performClick()
        verify { mockOnHelp() }

        composeTestRule.onNodeWithText("Logout").performClick()
        verify { mockOnLogout() }
    }
}
