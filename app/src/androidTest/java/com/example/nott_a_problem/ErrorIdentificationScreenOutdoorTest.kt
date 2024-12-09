package com.example.nott_a_problem

import com.example.nott_a_problem.report.screens.outdoor.ErrorIdentificationScreenOutdoor

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.nott_a_problem.repository.uploadReportToFirestoreOutdoor
import com.google.firebase.auth.FirebaseAuth
import io.mockk.*
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ErrorIdentificationScreenOutdoorTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    @Before
    fun setup() {

        navController = mockk(relaxed = true)
        auth = mockk(relaxed = true)

    }

    @Test
    fun errorIdentificationScreenOutdoorTest() {
        val capturedImageUriString = "https://example.com/image.jpg"
        val area = "Outdoor Area"
        val className = "Outdoor Event"
        val subclassName = "Sports Event"
        val userID = "testUserID"

        every { auth.currentUser?.uid } returns userID

        composeTestRule.setContent {
            ErrorIdentificationScreenOutdoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                subclassName = subclassName
            )
        }

        composeTestRule.onNodeWithText("Nott-A-Problem").assertIsDisplayed()

        composeTestRule.onNodeWithText("Class: $className").assertIsDisplayed()
        composeTestRule.onNodeWithText("Subclass: $subclassName").assertIsDisplayed()
        composeTestRule.onNodeWithText("Area: $area").assertIsDisplayed()

        composeTestRule.onNodeWithText("Yes").performClick()

        verify {
            uploadReportToFirestoreOutdoor(
                capturedImageUriString,
                area,
                className,
                subclassName,
                userID,
                any(),
                any()
            )
        }

        composeTestRule.onNodeWithText("No").performClick()
    }
}
