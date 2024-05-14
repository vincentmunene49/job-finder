package com.example.jobfinder.admin.main.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.admin.job_posting.presentation.SelfJobListingScreen
import com.example.jobfinder.admin.job_posting.presentation.SelfJobListingScreenContent
import com.example.jobfinder.admin.main.navigation.HomeNavigation
import com.example.jobfinder.admin.post.presentation.PostJobScreen
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.profile.presentation.ProfileScreen

@Composable
fun MainAdminAppScreens(
    navHostController: NavHostController = rememberNavController(),
    adminSharedViewModel: AdminSharedViewModel = hiltViewModel()

) {
    val bottomItems = listOf(
        BottomNavigationItem(
            title = Routes.AdminHome.route,
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomNavigationItem(
            title = Routes.PostJob.route,
            unselectedIcon = Icons.Outlined.Description,
            selectedIcon = Icons.Filled.Description
        ),
        BottomNavigationItem(
            title = Routes.AdminProfile.route,
            unselectedIcon = Icons.Outlined.NoteAlt,
            selectedIcon = Icons.Filled.NoteAlt
        )
    )

    val navStackBackStackEntry by navHostController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            if (adminSharedViewModel.state.showBottomNavigation) {
                NavigationBar {
                    bottomItems.forEach { item ->
                        val selected =
                            navStackBackStackEntry?.destination?.route?.lowercase() == item.title.lowercase()

                        NavigationBarItem(
                            selected = selected,
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                )
                            },
                            onClick = {
                                navHostController.navigate(item.title.lowercase()) {
                                    popUpTo(navHostController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                )
                            })
                    }
                }
            }

        }
    ) { padding ->

        NavHost(navController = navHostController, startDestination = Routes.AdminHome.route) {
            composable(route = Routes.AdminHome.route) {
                HomeNavigation(adminSharedViewModel)

            }

            composable(route = Routes.PostJob.route) {
                PostJobScreen()
            }

            composable(route = Routes.AdminProfile.route) {
                SelfJobListingScreen()
            }
        }
    }

}

private data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)