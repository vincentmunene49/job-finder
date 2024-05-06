package com.example.jobfinder.user.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.user.apply.presentation.JobApplicationScreen
import com.example.jobfinder.user.apply.presentation.SuccessApplicationScreen
import com.example.jobfinder.user.home.presentation.HomeScreen
import com.example.jobfinder.user.main.common.presentation.MainScreenSharedViewModel
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.user.view_job.presentation.JobDetailsScreen

@Composable
fun HomeNavigation(viewModel: MainScreenSharedViewModel) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            viewModel.showBottomNavigation(true)
            HomeScreen(navHostController)
        }
        composable(route = Routes.JobDetails.route) {
            viewModel.showBottomNavigation(false)
            JobDetailsScreen(navHostController,true)
        }

        composable(route = Routes.Apply.route) {
            viewModel.showBottomNavigation(false)

            JobApplicationScreen(navHostController)
        }

        composable(route = Routes.Success.route){
            viewModel.showBottomNavigation(false)
            SuccessApplicationScreen(navController = navHostController)
        }
    }

}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
