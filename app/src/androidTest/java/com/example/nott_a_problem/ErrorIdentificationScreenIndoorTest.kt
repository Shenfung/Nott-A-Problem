package com.example.nott_a_problem

import com.example.nott_a_problem.report.screens.indoor.ErrorIdentificationScreenIndoor

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.nott_a_problem.repository.uploadReportToFirestoreIndoor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class ErrorIdentificationScreenIndoorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorIdentificationScreenIndoor_displaysCorrectUI_andHandlesYesClick() {
        val mockNavController = mockk<NavController>(relaxed = true)

        mockkStatic("com.example.nott_a_problem.repository.UploadFunctionsKt")
        every { uploadReportToFirestoreIndoor(any(), any(), any(), any(), any(), any(), any(), any()) } answers {
            val onSuccess = arg<() -> Unit>(5)
            onSuccess()
        }

        val testUri = "content://test/image.png"
        val testArea = "Test Area"
        val testRoom = "123"
        val testClass = "Error Class"
        val testSubclass = "Error Subclass"
        val testUserID = "user123"

        composeTestRule.setContent {
            ErrorIdentificationScreenIndoor(
                navController = mockNavController,
                capturedImageUriString = testUri,
                area = testArea,
                roomNumber = testRoom,
                className = testClass,
                subclassName = testSubclass,
            )
        }

        composeTestRule.onNodeWithText("Nott-A-Problem").assertIsDisplayed()
        composeTestRule.onNodeWithText("Error Identified").assertIsDisplayed()
        composeTestRule.onNodeWithText("Class: $testClass").assertIsDisplayed()
        composeTestRule.onNodeWithText("Subclass: $testSubclass").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yes").assertIsDisplayed()
        composeTestRule.onNodeWithText("No").assertIsDisplayed()

        composeTestRule.onNodeWithText("Yes").performClick()

        verify { mockNavController.navigate("submitted") }

        verify {
            uploadReportToFirestoreIndoor(
                testUri,
                testArea,
                testRoom,
                testClass,
                testSubclass,
                testUserID,
                any(),
                any()
            )
        }
    }

    @Test
    fun errorIdentificationScreenIndoor_displaysDialog_onNoClick() {

        val mockNavController = mockk<NavController>(relaxed = true)
        val testUri = "content://test/image.png"
        val testArea = "Test Area"
        val testRoom = "123"
        val testClass = "Error Class"
        val testSubclass = "Error Subclass"

        composeTestRule.setContent {
            ErrorIdentificationScreenIndoor(
                navController = mockNavController,
                capturedImageUriString = testUri,
                area = testArea,
                roomNumber = testRoom,
                className = testClass,
                subclassName = testSubclass,
            )
        }

        composeTestRule.onNodeWithText("No").performClick()

        composeTestRule.onNodeWithText("What did we get wrong?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Class").assertIsDisplayed()
        composeTestRule.onNodeWithText("Subclass").assertIsDisplayed()
    }
}
