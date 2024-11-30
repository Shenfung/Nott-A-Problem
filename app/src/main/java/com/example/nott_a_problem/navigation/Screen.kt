//package com.example.nott_a_problem
//
//sealed class Screen(val route:String){
//    object LoginScreen : Screen("login_screen")
//    object RegistrationScreen : Screen("registration_screen")
//    object HomeScreen : Screen("home_screen")
//    object ChangePasswordScreen : Screen("change_password_screen")
//    object ChangeProfilePictureScreen : Screen("change_profile_picture_screen")
//    object RegistrationScreen : Screen("registration_screen")
//
//
//
//    fun withArgs(vararg args: String): String {
//        return buildString {
//            append(route)
//            args.forEach { arg ->
//                append("/$arg")
//            }
//        }
//    }
//
//}
