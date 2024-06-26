package com.example.jobfinder.user.main.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.common.util.JOB_ID
import com.example.jobfinder.user.applications.presentation.ApplicationListScreen
import com.example.jobfinder.user.main.common.presentation.MainScreenSharedViewModel
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.user.view_job.presentation.JobDetailsScreen
import com.example.jobfinder.user.view_job.presentation.JobDetailsViewModel

@Composable
fun ApplicationsNavigation(viewModel: MainScreenSharedViewModel) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.Applications.route) {
        composable(route = Routes.Applications.route) {
            viewModel.showBottomNavigation(true)
            ApplicationListScreen(navHostController)
        }
        composable(route = "${Routes.JobDetails.route}/{$JOB_ID}",
            arguments = listOf(navArgument(JOB_ID) {
                type = NavType.StringType
            })
        ) {
            viewModel.showBottomNavigation(false)
            val jobDetailViewModel: JobDetailsViewModel = hiltViewModel()
            jobDetailViewModel.initJobFunction(it.arguments?.getString(JOB_ID)!!)
            JobDetailsScreen(navHostController, false, viewModel = jobDetailViewModel)
        }
    }
}