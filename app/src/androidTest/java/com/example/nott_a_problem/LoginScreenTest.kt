package com.example.nott_a_problem.authentication.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import com.example.nott_a_problem.viewModel.AuthResponse
import com.example.nott_a_problem.viewModel.AuthenticationManager
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockNavController = mockk<NavHostController>(relaxed = true)
    private val mockAuthenticationManager = mockk<AuthenticationManager>()

    @Test
    fun testSuccessfulLoginNavigatesToHome() {
        every {
            mockAuthenticationManager.loginWithEmail(any(), any())
        } returns flowOf(AuthResponse.Success(mockk(relaxed = true)))

        composeTestRule.setContent {
            LoginScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithText("University Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        composeTestRule.onNodeWithText("Sign-in").performClick()

        verify {
            mockNavController.navigate("home")
        }
    }

    @Test
    fun testFailedLoginShowsToast() {
        val errorMessage = "Invalid credentials"
        every {
            mockAuthenticationManager.loginWithEmail(any(), any())
        } returns flowOf(AuthResponse.Error(errorMessage))

        composeTestRule.setContent {
            LoginScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithText("University Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("wrongpassword")

        composeTestRule.onNodeWithText("Sign-in").performClick()

        composeTestRule.waitForIdle()
        verify {
            mockAuthenticationManager.loginWithEmail("test@example.com", "wrongpassword")
        }
    }
}
