package com.example.nott_a_problem

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.example.nott_a_problem.authentication.screens.RegistrationScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.MockKAnnotations
import io.mockk.awaits
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    private lateinit var auth: FirebaseAuth

    @MockK
    private lateinit var db: FirebaseFirestore

    @MockK
    private lateinit var navController: NavController

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test successful registration`() {
        every { auth.createUserWithEmailAndPassword("hfyze@nottingham.edu.my", "password123") } returns mockk {
            every { isSuccessful } returns true
            every { result } returns mockk {
                every { user } returns mockk {
                    every { uid } returns "userId"
                }
            }
        }

        every { db.collection("users").document("userId").set(any()) } just awaits

        composeTestRule.setContent {
            RegistrationScreen(navController)
        }

        composeTestRule.onNodeWithTag("EmailField").performTextInput("hfyze@nottingham.edu.my")
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("ConfirmPasswordField").performTextInput("password123")
        composeTestRule.onNodeWithText("Register Now").performClick()

        verify { navController.navigate("get_started") }
        verify {
            db.collection("users").document("userId").set(match {
                it["email"].toString() == "hfyze@nottingham.edu.my"
            })
        }
    }

    @Test
    fun `test failed registration due to invalid email domain`() {

        every { auth.createUserWithEmailAndPassword("user@gmail.com", "password123") } returns mockk {
            every { isSuccessful } returns false
            every { exception?.message } returns "Invalid email domain"
        }

        composeTestRule.setContent {
            RegistrationScreen(navController)
        }

        composeTestRule.onNodeWithTag("EmailField").performTextInput("user@gmail.com")
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("ConfirmPasswordField").performTextInput("password123")
        composeTestRule.onNodeWithText("Register Now").performClick()

    }
}
