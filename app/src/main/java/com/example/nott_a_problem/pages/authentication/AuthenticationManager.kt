package com.example.nott_a_problem

import android.content.Context
import android.util.Log
import com.example.nott_a_problem.pages.authentication.AuthResponse
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

class AuthenticationManager(val context: Context) {
    private val auth = Firebase.auth

    fun loginWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        Log.e("AuthenticationManager", "Logging in with email: $email")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("AuthenticationManager", "Login successful")
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        trySend(AuthResponse.Success(user = firebaseUser))
                    } else {
                        trySend(AuthResponse.Error(message = "User is null after successful login"))
                    }
                } else {
                    Log.e("AuthenticationManager", "Login failed: ${task.exception?.message}")
                    trySend(AuthResponse.Error(message = task.exception?.message ?: "Unknown error"))
                }
            }
        awaitClose()
    }
}
