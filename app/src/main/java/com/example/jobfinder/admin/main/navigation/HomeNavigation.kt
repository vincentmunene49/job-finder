package com.example.jobfinder.admin.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.admin.applications.presentation.ViewApplicationsScreen
import com.example.jobfinder.admin.candidate.presentation.CandidateApplicationScreen
import com.example.jobfinder.admin.main.presentation.AdminSharedViewModel
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

        composable(route = Routes.Applicant.route) {
            viewModel.showBottomNavigation(false)
            CandidateApplicationScreen(navHostController)
        }
    }
}