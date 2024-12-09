package com.example.nott_a_problem

import android.net.Uri
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.nott_a_problem.locations.screens.IndoorCheckScreen
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class IndoorCheckScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockNavController: NavController

    @Before
    fun setup() {
        mockNavController = mockk(relaxed = true)
    }

    @Test
    fun `should navigate to error screen when area is outdoor`() {
        val capturedImageUriString = "mockUri"
        val area = "Block X; Unknown Area"
        val className = "MockClass"
        val subclassName = "MockSubclass"

        composeTestRule.setContent {
            IndoorCheckScreen(
                navController = mockNavController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                subclassName = subclassName
            )
        }

        composeTestRule.waitForIdle()

        verify {
            mockNavController.navigate("error_identification_outdoor/$capturedImageUriString/$area/$className/$subclassName")
        }
    }

    @Test
    fun `should show RoomNumberDialog and navigate to indoor error screen when area is indoor`() {
        val capturedImageUriString = "mockUri"
        val area = "Block A; Trent Building"
        val className = "MockClass"
        val subclassName = "MockSubclass"

        composeTestRule.setContent {
            IndoorCheckScreen(
                navController = mockNavController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                subclassName = subclassName
            )
        }

        composeTestRule.onNodeWithTag("RoomNumberDialog").assertIsDisplayed()
        composeTestRule.onNodeWithTag("RoomNumberDialog").performClick() // Mock user interaction

        val roomNumber = "101"
        verify {
            mockNavController.navigate("error_identification_indoor/${Uri.encode(capturedImageUriString)}/${Uri.encode(area)}/${Uri.encode(roomNumber)}/${Uri.encode(className)}/${Uri.encode(subclassName)}")
        }
    }
}
