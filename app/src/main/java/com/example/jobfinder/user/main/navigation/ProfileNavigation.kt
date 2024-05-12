package com.example.jobfinder.user.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.auth.login.presentation.LoginScreen
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.profile.presentation.ProfileScreen

@Composable
fun ProfileNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Profile.route) {
        composable(route = Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Routes.Login.route) {
            LoginScreen(navHostController = navController)
        }
    }
}