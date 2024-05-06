package com.example.jobfinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.jobfinder.admin.main.presentation.MainAdminAppScreens
import com.example.jobfinder.auth.login.presentation.LoginScreen
import com.example.jobfinder.auth.sign_up.presentation.SignUpScreen
import com.example.jobfinder.user.main.presentation.MainUserAppScreens

@Composable
fun ApplicationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Auth.route
    ) {
        navigation(
            route = Routes.Auth.route,
            startDestination = Routes.Login.route
        ){
            composable(route = Routes.Login.route){
                 LoginScreen(navController)
            }
            composable(route = Routes.Register.route){
                SignUpScreen(navController)
            }
        }

        composable(route = Routes.MainUserScreens.route){
            MainUserAppScreens()
        }
        //add the MainAppScreensForAdmin
        composable(route = Routes.MainAdminScreens.route){
            MainAdminAppScreens()
        }

    }
}