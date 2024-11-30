//package com.example.nott_a_problem.pages.dashboard.components
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import com.example.nott_a_problem.R
//
//data class BottomNavigationItem(
//    val title: String,
//    val selectedIcon: Painter,
//    val unselectedIcon: Painter,
//    val hasNews: Boolean,
//    val badgeCount: Int? = null,
//)
//
//@Composable
//fun MyBottomNavigationBar() {
//    val items = listOf(
//        BottomNavigationItem(
//            title = "Home",
//            selectedIcon = painterResource(R.drawable.home),
//            unselectedIcon = painterResource(R.drawable.home_outline),
//            hasNews = false,
//        ),
//        BottomNavigationItem(
//            title = "Camera",
//            selectedIcon = painterResource(R.drawable.camera),
//            unselectedIcon = painterResource(R.drawable.camera_outline),
//            hasNews = false,
//        ),
//    )
//    var selectedItemIndex by remember { mutableStateOf(0) }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Scaffold(
//            bottomBar = {
//                NavigationBar {
//                    items.forEachIndexed { index, item ->
//                        NavigationBarItem(
//                            selected = selectedItemIndex == index,
//                            onClick = { selectedItemIndex = index },
//                            label = { Text(text = item.title) },
//                            alwaysShowLabel = false,
//                            icon = {
//                                BadgedBox(
//                                    badge = {
//                                        if (item.badgeCount != null) {
//                                            Badge { Text(text = item.badgeCount.toString()) }
//                                        } else if (item.hasNews) {
//                                            Badge()
//                                        }
//                                    }
//                                ) {
//                                    Icon(
//                                        painter = if (index == selectedItemIndex) {
//                                            item.selectedIcon
//                                        } else {
//                                            item.unselectedIcon
//                                        },
//                                        contentDescription = item.title,
//                                    )
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        ) { paddingValues ->
//            Surface(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                color = MaterialTheme.colorScheme.background
//            ) {
//                // Your content here
//            }
//        }
//    }
//}
