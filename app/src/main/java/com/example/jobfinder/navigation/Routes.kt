package com.example.jobfinder.navigation

sealed class Routes(val route: String) {

    object Login : Routes(LOGIN)
    object Register : Routes(REGISTER)

    object MainUserScreens : Routes(MAINUSERSCREENS)

    object MainAdminScreens : Routes(MAINADMINSCREENS)
    object Root : Routes(ROOT)
    object Auth : Routes(AUTH)
    object Home : Routes(HOME)
    object AdminHome : Routes(ADMIN_HOME)
    object JobDetails : Routes(JOB_DETAILS)
    object Apply : Routes(APPLy)
    object Profile : Routes(PROFILE)

    object PostJob : Routes(POST_JOB)

    object AdminProfile : Routes(ADMIN_PROFILE)
    object Applications : Routes(APPLICATIONS)

    object Success : Routes(SUCCESS)

    object Applicant : Routes(APPLICANTS)
}


const val LOGIN = "login"
const val POST_JOB  = "Post Job"
const val ADMIN_PROFILE = "Profile"
const val ADMIN_HOME = "Home"
const val REGISTER = "register"
const val HOME = "Home"
const val APPLICANTS = "Applicants"
const val JOB_DETAILS = "job_details"
const val APPLy = "apply"
const val PROFILE = "Profile"
const val APPLICATIONS = "Applications"
const val AUTH = "auth"
const val MAINUSERSCREENS = "main_user_screens"
const val MAINADMINSCREENS = "main_admin_screens"
const val ROOT = "root"
const val SUCCESS = "success"
