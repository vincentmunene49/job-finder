package com.example.jobfinder.user.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.common.util.JOB_ID
import com.example.jobfinder.common.util.JOB_POSTER_ID
import com.example.jobfinder.user.apply.presentation.JobApplicationScreen
import com.example.jobfinder.user.apply.presentation.SuccessApplicationScreen
import com.example.jobfinder.user.home.presentation.HomeScreen
import com.example.jobfinder.user.main.common.presentation.MainScreenSharedViewModel
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.user.apply.presentation.JobApplicationViewModel
import com.example.jobfinder.user.view_job.presentation.JobDetailsScreen
import com.example.jobfinder.user.view_job.presentation.JobDetailsViewModel
import timber.log.Timber

@Composable
fun HomeNavigation(viewModel: MainScreenSharedViewModel) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            viewModel.showBottomNavigation(true)
            HomeScreen(navHostController)
        }
        composable(
            route = "${Routes.JobDetails.route}/{$JOB_ID}",
            arguments = listOf(navArgument(JOB_ID) {
                type = NavType.StringType
            })
        ) {
            viewModel.showBottomNavigation(false)
            val jobDetailViewModel: JobDetailsViewModel = hiltViewModel()
            jobDetailViewModel.initJobFunction(it.arguments?.getString(JOB_ID)!!)
            JobDetailsScreen(navHostController, true, viewModel = jobDetailViewModel)
        }

        composable(
            route = "${Routes.Apply.route}/{$JOB_ID}/{$JOB_POSTER_ID}",
            arguments = listOf(
                navArgument(JOB_ID) {
                type = NavType.StringType
            },
                navArgument(JOB_POSTER_ID) {
                type = NavType.StringType
            })
        ) {
            Timber.tag("HOMENAVIGATION").d("JobId: ${it.arguments?.getString(JOB_ID)}")
            viewModel.showBottomNavigation(false)
            val jobApplicationViewModel: JobApplicationViewModel = hiltViewModel()
            jobApplicationViewModel.init(it.arguments?.getString(JOB_ID)!!, it.arguments?.getString(JOB_POSTER_ID)!!)
            JobApplicationScreen(navHostController, viewModel = jobApplicationViewModel)
        }

        composable(route = Routes.Success.route) {
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
