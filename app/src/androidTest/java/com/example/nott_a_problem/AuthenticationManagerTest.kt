package com.example.nott_a_problem

import com.example.nott_a_problem.viewModel.AuthResponse
import com.example.nott_a_problem.viewModel.AuthenticationManager
import com.google.android.gms.tasks.Task
import android.content.Context
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticationManagerTest {

    private lateinit var context: Context
    private lateinit var authManager: AuthenticationManager
    private lateinit var mockAuth: FirebaseAuth
    private lateinit var mockTask: Task<AuthResult>
    private lateinit var mockAuthResult: AuthResult
    private lateinit var mockUser: FirebaseUser

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        context = mockk(relaxed = true)
        mockAuth = mockk(relaxed = true)
        mockTask = mockk(relaxed = true)
        mockAuthResult = mockk(relaxed = true)
        mockUser = mockk(relaxed = true)

        authManager = AuthenticationManager(context)
        every { mockAuth.currentUser } returns mockUser
    }

    @Test
    fun `test loginWithEmail successful`() = runBlockingTest {
        val email = "test@example.com"
        val password = "password123"
        val expectedUid = "mockedUid"

        every { mockTask.isSuccessful } returns true
        every { mockTask.result } returns mockAuthResult
        every { mockAuthResult.user } returns mockUser
        every { mockUser.uid } returns expectedUid

        every {
            mockAuth.signInWithEmailAndPassword(email, password)
        } returns mockTask

        val result = authManager.loginWithEmail(email, password).first()

        assertTrue(result is AuthResponse.Success)
        assertTrue((result as AuthResponse.Success).user.uid == expectedUid)

        verify {
            mockAuth.signInWithEmailAndPassword(email, password)
            mockAuth.currentUser
        }
    }

    @Test
    fun `test loginWithEmail failure`() = runBlockingTest {

        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Login failed"

        every { mockTask.isSuccessful } returns false
        every { mockTask.exception } returns Exception(errorMessage)

        every {
            mockAuth.signInWithEmailAndPassword(email, password)
        } returns mockTask

        val result = authManager.loginWithEmail(email, password).first()

        assertTrue(result is AuthResponse.Error)
        assertTrue((result as AuthResponse.Error).message == errorMessage)

        verify {
            mockAuth.signInWithEmailAndPassword(email, password)
        }
    }
}
