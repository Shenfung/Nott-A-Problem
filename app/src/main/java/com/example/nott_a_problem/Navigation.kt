//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.nott_a_problem.Screen
//import androidx.navigation.NavType
//import androidx.navigation.navArgument
//import com.example.nott_a_problem.DetailScreen
//import com.example.nott_a_problem.MainScreen
//
//@Composable
//fun Navigation(){
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
//        composable(route = Screen.MainScreen.route){
//            MainScreen(navController = navController)
//        }
//        composable(
//            route = Screen.DetailScreen.route + "/{name}",
//            arguments = listOf(
//                navArgument("name"){
//                    type = NavType.StringType
//                    defaultValue = "Philipp" //optional
//                    nullable = true
//                }
//            )
//        ){ entry ->
//            DetailScreen(name = entry.arguments?.getString("name"))
//        }
//    }
//}