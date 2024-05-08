package com.example.jobfinder.user.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.user.applications.presentation.ApplicationListScreen
import com.example.jobfinder.user.main.common.presentation.MainScreenSharedViewModel
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.user.view_job.presentation.JobDetailsScreen

@Composable
fun ApplicationsNavigation(viewModel: MainScreenSharedViewModel) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.Applications.route) {
        composable(route = Routes.Applications.route) {
            viewModel.showBottomNavigation(true)
            ApplicationListScreen(navHostController)
        }
        composable(route = Routes.JobDetails.route) {
            viewModel.showBottomNavigation(false)
            JobDetailsScreen(navHostController,false)
        }
    }
}