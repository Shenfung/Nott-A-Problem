package com.example.nott_a_problem

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nott_a_problem.View.Screens.ChangePasswordScreen
import com.example.nott_a_problem.View.Screens.NoEventDetectedScreenContent
import com.example.nott_a_problem.View.Screens.SubmittedScreen
import com.example.nott_a_problem.View.Screens.GetStartedScreen
import com.example.nott_a_problem.View.Screens.LoginScreen
import com.example.nott_a_problem.View.Screens.RegistrationScreen
import com.example.nott_a_problem.View.ErrorScreens.ClassDescriptionScreenIndoor
import com.example.nott_a_problem.View.ErrorScreens.ClassDescriptionScreenOutdoor
import com.example.nott_a_problem.View.ErrorScreens.ErrorIdentificationScreenIndoor
import com.example.nott_a_problem.View.ErrorScreens.ErrorIdentificationScreenOutdoor
import com.example.nott_a_problem.View.ErrorScreens.SubclassDescriptionScreenIndoor
import com.example.nott_a_problem.View.ErrorScreens.SubclassDescriptionScreenOutdoor
import com.example.nott_a_problem.View.Screens.CameraScreen
import com.example.nott_a_problem.View.Screens.HomeScreen
import com.example.nott_a_problem.View.Screens.LoadingScreen
import com.example.nott_a_problem.View.Screens.IndoorCheckScreen
import com.example.nott_a_problem.View.Screens.OutsideCampusScreen



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
    //authentication
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
        composable("indoorCheckScreen/{capturedImageUriString}/{area}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            IndoorCheckScreen(
                navController = navController,
                capturedImageUriString = capturedImageUriString, // Replace with your photo file path
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
                capturedImageUriString = capturedImageUriString, // Replace with the actual path to the photo
                area = area,  // Pass appropriate data here
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
                capturedImageUriString = capturedImageUriString, // Replace with the actual path to the photo
                area = area,  // Pass appropriate data here
                className = className,
                subclassName = subclassName,
            )
        }

        composable("submitted") { SubmittedScreen(navController) }
    }
}
