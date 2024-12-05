//package com.example.nott_a_problem.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.platform.LocalContext
//import com.example.nott_a_problem.MainActivity
//
//@Composable
//fun AppNavigationWithPermissionHandling() {
//    val context = LocalContext.current as MainActivity
//
//    LaunchedEffect(Unit) {
//        // Initial permission check if needed
//        context.checkAndRequestCameraPermission()
//    }
//
//    AppNavigation(onCameraClick = {
//        // Handle the Camera permission logic
//        if (context.checkAndRequestCameraPermission()) {
//            context.launchCamera()
//        }
//    })
//}
