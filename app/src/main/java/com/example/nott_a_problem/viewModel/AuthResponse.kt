package com.example.nott_a_problem.viewModel

import com.google.firebase.auth.FirebaseUser

sealed class AuthResponse {
    data class Success(val user: FirebaseUser) : AuthResponse()
    data class Error(val message: String) : AuthResponse()
}
