package com.example.nott_a_problem.pages.authentication.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nott_a_problem.AuthenticationManager
import com.example.nott_a_problem.R
import com.example.nott_a_problem.pages.authentication.AuthResponse
import com.example.nott_a_problem.pages.authentication.LoginBackground
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val authenticationManager = remember {
        AuthenticationManager(context).also {
            Log.e("LoginScreen", "AuthenticationManager initialized")
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )

    LoginBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Nott-A-Problem",
            fontFamily = customFontFamily,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Access to the fixes on campus.\nReport faulty facilities by just taking a picture and submitting.",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(    modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // Adjust padding around the box
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f), // Semi-transparent white
                shape = RoundedCornerShape(16.dp) // Rounded corners for the box
            )
            .padding(16.dp) // Padding inside the box for its content
        ) {
            Column{
            Text(
                text = "University Email",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = email,
                onValueChange = { newValue ->
                    Log.e("LoginScreen", "Email changed to $newValue")
                    email = newValue
                },
                placeholder = {
                    Text(text = "hfyze@nottingham.edu.my")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Email, contentDescription = null)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Password",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = { newValue ->
                    Log.e("LoginScreen", "Password changed")
                    password = newValue
                },
                placeholder = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Lock, contentDescription = null)
                },
                trailingIcon = {
                    val visibilityIcon =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = visibilityIcon,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Perform the login or any other action when the user is done
                        Log.e("LoginScreen", "Form submitted")
                        keyboardController?.hide()
                    }
                )
            )

                val forgotPasswordAnnotatedString = buildAnnotatedString {
                    pushStringAnnotation(
                        tag = "FORGOT_PASSWORD",
                        annotation = "reset_password_page"
                    ) // Tag to recognize the hyperlink
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Forgot Password?")
                    }
                    pop() // End annotation
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    ClickableText(
                        text = forgotPasswordAnnotatedString,
                        style = MaterialTheme.typography.bodyMedium,
                        onClick = { offset ->
                            forgotPasswordAnnotatedString.getStringAnnotations(
                                tag = "FORGOT_PASSWORD",
                                start = offset,
                                end = offset
                            )
                                .firstOrNull()?.let {
                                    Log.e("LoginScreen", "Navigating to reset password page")
                                    navController.navigate("change_password") // Navigate to the reset password screen
                                }
                        }
                    )
                }

            Button(
                onClick = {
                    Log.e("LoginScreen", "Sign-in button clicked")
                    authenticationManager.loginWithEmail(email, password)
                        .onEach { response ->
                            when (response) {
                                is AuthResponse.Success -> {
                                    Log.e("LoginScreen", "Login success, user: ${response.user.email}")
                                    navController.navigate("home") // Replace with your main screen's route
                                }
                                is AuthResponse.Error -> {
                                    Log.e("LoginScreen", "Login failed: ${response.message}")
                                    // Show error message, e.g., a Toast
                                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        .launchIn(coroutineScope)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign-in",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            val annotatedString = buildAnnotatedString {
                append("Not registered yet? ")
                pushStringAnnotation(
                    tag = "REGISTER",
                    annotation = "register_page"
                ) // Tag to recognize the hyperlink
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Register Now")
                }
                pop() // End annotation
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                ClickableText(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "REGISTER",
                            start = offset,
                            end = offset
                        )
                            .firstOrNull()?.let {
                                Log.e("LoginScreen", "Navigating to registration page")
                                navController.navigate("register")
                            }
                    }
                )
            }
            }
        }
    }
}
