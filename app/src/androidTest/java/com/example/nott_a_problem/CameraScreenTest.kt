package com.example.nott_a_problem

import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.nott_a_problem.classification.screens.CameraScreen
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CameraScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockNavController: NavController

    @Before
    fun setUp() {
        mockNavController = mockk(relaxed = true)
        mockkStatic(Uri::class)
        mockkStatic(Toast::class)
    }

    @Test
    fun `clicking Take Picture launches camera intent`() {
        every { Uri.encode(any()) } returns "mock_encoded_uri"

        composeTestRule.setContent {
            CameraScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithText("Take Picture").performClick()

    }

    @Test
    fun `clicking Get Results navigates to loading screen when image is captured`() {
        every { Uri.encode(any()) } returns "mock_encoded_uri"

        composeTestRule.setContent {
            CameraScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithText("Take Picture").performClick()
        composeTestRule.onNodeWithText("Get Results").performClick()

        verify { mockNavController.navigate("loading_screen/mock_encoded_uri") }
    }
}