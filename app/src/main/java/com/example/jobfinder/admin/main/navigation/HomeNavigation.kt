package com.example.jobfinder.admin.main.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobfinder.admin.applications.presentation.ViewApplicationsScreen
import com.example.jobfinder.admin.candidate.presentation.CandidateApplicationScreen
import com.example.jobfinder.admin.candidate.presentation.CandidateViewmodel
import com.example.jobfinder.admin.main.presentation.AdminSharedViewModel
import com.example.jobfinder.common.util.JOB_ID
import com.example.jobfinder.common.util.JOB_POSTER_ID
import com.example.jobfinder.navigation.Routes

@Composable
fun HomeNavigation(
    viewModel: AdminSharedViewModel
) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.AdminHome.route) {
        composable(route = Routes.AdminHome.route) {
            viewModel.showBottomNavigation(true)
            ViewApplicationsScreen(navController = navHostController)
        }

        composable(
            route = "${Routes.Applicant.route}/{$JOB_POSTER_ID}",
            arguments = listOf(navArgument(JOB_POSTER_ID) {
                type = NavType.StringType
            })
        ) {
            viewModel.showBottomNavigation(false)
            val candidateViewmodel:CandidateViewmodel = hiltViewModel()
            candidateViewmodel.initialize(it.arguments?.getString(JOB_POSTER_ID)?:"")
            CandidateApplicationScreen(navHostController, candidateViewmodel)
        }
    }
}