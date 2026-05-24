package uklo.edu.mk.pmp.studentapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uklo.edu.mk.pmp.studentapp.screens.home.HomeScreen
import uklo.edu.mk.pmp.studentapp.screens.login.LoginScreen
import uklo.edu.mk.pmp.studentapp.screens.notification.NotificationScreen
import uklo.edu.mk.pmp.studentapp.screens.profile.ProfileScreen
import uklo.edu.mk.pmp.studentapp.screens.report.ReportScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onProfileClick = {
                    navController.navigate("profile")
                },
                onReportClick = {
                    navController.navigate("report")
                },
                onNotificationClick = {
                    navController.navigate("notification")
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("report") {
            ReportScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("notification") {
            NotificationScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}