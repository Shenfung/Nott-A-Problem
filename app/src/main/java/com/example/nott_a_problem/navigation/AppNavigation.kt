package com.example.nott_a_problem.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nott_a_problem.dashboard.screens.ChangePasswordScreen
import com.example.nott_a_problem.classification.screens.NoEventDetectedScreenContent
import com.example.nott_a_problem.report.screens.SubmittedScreen
import com.example.nott_a_problem.authentication.screens.GetStartedScreen
import com.example.nott_a_problem.authentication.screens.LoginScreen
import com.example.nott_a_problem.authentication.screens.RegistrationScreen
import com.example.nott_a_problem.report.screens.indoor.ClassDescriptionScreenIndoor
import com.example.nott_a_problem.report.screens.outdoor.ClassDescriptionScreenOutdoor
import com.example.nott_a_problem.report.screens.indoor.ErrorIdentificationScreenIndoor
import com.example.nott_a_problem.report.screens.outdoor.ErrorIdentificationScreenOutdoor
import com.example.nott_a_problem.report.screens.indoor.SubclassDescriptionScreenIndoor
import com.example.nott_a_problem.report.screens.outdoor.SubclassDescriptionScreenOutdoor
import com.example.nott_a_problem.classification.screens.CameraScreen
import com.example.nott_a_problem.dashboard.screens.HomeScreen
import com.example.nott_a_problem.classification.screens.LoadingScreen
import com.example.nott_a_problem.locations.screens.IndoorCheckScreen
import com.example.nott_a_problem.locations.screens.OutsideCampusScreen



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("register") { RegistrationScreen(navController) }
        composable("get_started") { GetStartedScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("camera_screen") { CameraScreen(navController) }
        composable("no_event_detected") { NoEventDetectedScreenContent(navController) }
        composable("home") { HomeScreen(navController) }
        composable("loading_screen/{imageUri}") { backStackEntry ->
            val encodedImageUri = backStackEntry.arguments?.getString("imageUri")
            val imageUri = encodedImageUri?.let { Uri.parse(it) }
            if (imageUri != null) {
                LoadingScreen(navController = navController, capturedImageUriString = imageUri)
            }
        }
        composable("outside_campus_screen") { OutsideCampusScreen(navController) }
        composable("submitted") { SubmittedScreen(navController) }

        composable("indoorCheckScreen/{capturedImageUriString}/{area}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            IndoorCheckScreen(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                subclassName = subclassName
            )
        }
        composable("error_identification_indoor/{capturedImageUriString}/{area}/{roomNumber}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val roomNumber = Uri.decode(backStackEntry.arguments?.getString("roomNumber"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            ErrorIdentificationScreenIndoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                roomNumber = roomNumber,
                className = className,
                subclassName = subclassName,
            )
        }

        composable("class_description_screen_indoor/{capturedImageUriString}/{area}/{roomNumber}/{userID}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val roomNumber = Uri.decode(backStackEntry.arguments?.getString("roomNumber"))
            val userID = Uri.decode(backStackEntry.arguments?.getString("userID"))

            ClassDescriptionScreenIndoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                roomNumber = roomNumber,
                userID = userID,
            )
        }

        composable("subclass_description_screen_indoor/{capturedImageUriString}/{area}/{roomNumber}/{className}/{userID}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val roomNumber = Uri.decode(backStackEntry.arguments?.getString("roomNumber"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val userID = Uri.decode(backStackEntry.arguments?.getString("userID"))

            SubclassDescriptionScreenIndoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                roomNumber = roomNumber,
                className = className,
                userID = userID,)

        }

        composable("subclass_description_screen_outdoor/{capturedImageUriString}/{area}/{className}/{roomNumber}/{userID}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val userID = Uri.decode(backStackEntry.arguments?.getString("userID"))

            SubclassDescriptionScreenOutdoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                userID = userID,)

        }

        composable("class_description_screen_outdoor/{capturedImageUriString}/{area}/{userID}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val userID = Uri.decode(backStackEntry.arguments?.getString("userID"))

            ClassDescriptionScreenOutdoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                userID = userID,
            )
        }

        composable("error_identification_outdoor/{capturedImageUriString}/{area}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            ErrorIdentificationScreenOutdoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString,
                area = area,
                className = className,
                subclassName = subclassName,
            )
        }
    }
}
