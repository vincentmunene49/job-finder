package com.example.jobfinder.navigation

sealed class Routes(val route: String) {

    object Login : Routes(LOGIN)
    object Register : Routes(REGISTER)

    object Main : Routes(MAIN)
    object Root : Routes(ROOT)
    object Auth : Routes(AUTH)
    object Home : Routes(HOME)
    object JobDetails : Routes(JOB_DETAILS)
    object Apply : Routes(APPLy)
    object Profile : Routes(PROFILE)
    object Applications : Routes(APPLICATIONS)

    object Success : Routes(SUCCESS)
}


const val LOGIN = "login"
const val REGISTER = "register"
const val HOME = "Home"
const val JOB_DETAILS = "job_details"
const val APPLy = "apply"
const val PROFILE = "Profile"
const val APPLICATIONS = "Applications"
const val AUTH = "auth"
const val MAIN = "main"
const val ROOT = "root"
const val SUCCESS = "success"
