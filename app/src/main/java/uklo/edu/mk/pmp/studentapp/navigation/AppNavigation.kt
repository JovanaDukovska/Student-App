package uklo.edu.mk.pmp.studentapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var selectedLanguage by remember {
        mutableStateOf("EN")
    }

    var isGuest by remember {
        mutableStateOf(false)
    }

    var isGoogleRestricted by remember {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                selectedLanguage = selectedLanguage,
                onLanguageChange = {
                    selectedLanguage = it
                },
                onLoginClick = {
                    isGuest = false
                    isGoogleRestricted = false

                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                onGuestClick = {
                    isGuest = true
                    isGoogleRestricted = false

                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                onGoogleRestrictedClick = {
                    isGuest = false
                    isGoogleRestricted = true

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
                selectedLanguage = selectedLanguage,
                onLanguageChange = {
                    selectedLanguage = it
                },
                isGuest = isGuest,
                isGoogleRestricted = isGoogleRestricted,
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
                selectedLanguage = selectedLanguage,
                onLanguageChange = {
                    selectedLanguage = it
                },
                isGuest = isGuest,
                isGoogleRestricted = isGoogleRestricted,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("report") {
            ReportScreen(
                selectedLanguage = selectedLanguage,
                onLanguageChange = {
                    selectedLanguage = it
                },
                isGuest = isGuest,
                isGoogleRestricted = isGoogleRestricted,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("notification") {
            NotificationScreen(
                selectedLanguage = selectedLanguage,
                onLanguageChange = {
                    selectedLanguage = it
                },
                isGuest = isGuest,
                isGoogleRestricted = isGoogleRestricted,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}