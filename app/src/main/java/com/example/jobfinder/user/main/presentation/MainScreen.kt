package com.example.jobfinder.user.main.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
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
import com.example.jobfinder.user.main.common.presentation.MainScreenSharedViewModel
import com.example.jobfinder.user.main.navigation.ApplicationsNavigation
import com.example.jobfinder.user.main.navigation.HomeNavigation
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.profile.presentation.ProfileScreen
import com.example.jobfinder.user.main.navigation.ProfileNavigation

@Composable
fun MainUserAppScreens(
    navHostController: NavHostController = rememberNavController(),
    mainScreenSharedViewModel: MainScreenSharedViewModel = hiltViewModel()
) {

    val bottomItems = listOf(
        BottomNavigationItem(
            title = Routes.Home.route,
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomNavigationItem(
            title = Routes.Applications.route,
            unselectedIcon = Icons.Outlined.Description,
            selectedIcon = Icons.Filled.Description
        ),
        BottomNavigationItem(
            title = Routes.Profile.route,
            unselectedIcon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person
        )
    )
    val navStackBackStackEntry by navHostController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            if (mainScreenSharedViewModel.state.showBottomNavigation) {
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

        NavHost(navController = navHostController, startDestination = Routes.Home.route) {
            composable(route = Routes.Home.route) {
                HomeNavigation(mainScreenSharedViewModel)
            }

            composable(route = Routes.Applications.route) {
                ApplicationsNavigation(mainScreenSharedViewModel)
            }

            composable(route = Routes.Profile.route) {
                ProfileScreen()
            }
        }

    }

}


private data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)