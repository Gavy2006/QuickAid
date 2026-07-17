//package com.example.quickaid.ui.theme.navigation
//
//import HomeScreen
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.*
//import com.example.quickaid.ui.theme.screens.*
//import com.example.quickaid.repositry.AuthRepository
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppNavigation() {
//
//    val navController = rememberNavController()
//    val repository = AuthRepository()
//
//    val currentRoute =
//        navController.currentBackStackEntryAsState().value?.destination?.route
//
//    val showBars = currentRoute !in listOf(
//        Screen.Login.route,
//        Screen.Signup.route
//    )
//    Scaffold(
//
//        topBar = {
//            if (showBars) {
//                TopAppBar(
//                    title = {
//                        Text("QuickAid")
//                    }
//                )
//            }
//        },
//
//        bottomBar = {
//
//            if (showBars) {
//
//
//            NavigationBar {
//
//                NavigationBarItem(
//                    selected = currentRoute == Screen.Home.route,
//                    onClick = {
//                        navController.navigate(Screen.Home.route) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.Home, null) },
//                    label = { Text("Home") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == Screen.Hospital.route,
//                    onClick = {
//                        navController.navigate(Screen.Hospital.route) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.CheckCircle, null) },
//                    label = { Text("Hospital") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == Screen.History.route,
//                    onClick = {
//                        navController.navigate(Screen.History.route) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.Search, null) },
//                    label = { Text("History") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == Screen.Profile.route,
//                    onClick = {
//                        navController.navigate(Screen.Profile.route) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.Person, null) },
//                    label = { Text("Profile") }
//                )
//            }
//        } }
//
//    ) { padding ->
//
//        NavHost(
//            navController = navController,
//            startDestination =
//                if (repository.isLoggedIn())
//                    Screen.Home.route
//                else
//                    Screen.Login.route,
//            modifier = Modifier.padding(padding)
//        ) {
//            composable(Screen.Login.route) {
//
//                LoginScreen(
//
//                    onLoginClick = { email, password ->
//
//                        CoroutineScope(Dispatchers.IO).launch {
//
//                            try {
//
//                                repository.login(email, password)
//
//                                withContext(Dispatchers.Main) {
//
//                                    navController.navigate(Screen.Home.route) {
//                                        popUpTo(Screen.Login.route) {
//                                            inclusive = true
//                                        }
//                                    }
//
//                                }
//
//                            } catch (e: Exception) {
//
//                                e.printStackTrace()
//
//                            }
//
//                        }
//
//                    },
//
//                    onSignupClick = {
//                        navController.navigate(Screen.Signup.route)
//                    }
//
//                )
//            }
//
//            composable(Screen.Signup.route) {
//                SignupScreen(
//                    onSignupClick = {
//                        navController.popBackStack()
//                    },
//                    onLoginClick = {
//                        navController.popBackStack()
//                    }
//                )
//            }
//            composable(Screen.Home.route) {
//                HomeScreen(navController)
//            }
//
//            composable(Screen.Hospital.route) {
//                HospitalScreen(navController)
//            }
//
//            composable(Screen.History.route) {
//                HistoryScreen(navController)
//            }
//
//            composable(Screen.Profile.route) {
//                ProfileScreen(navController)
//            }
//        }
//    }
//}

package com.example.quickaid.ui.theme.navigation

import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.quickaid.repositry.AuthRepository
import com.example.quickaid.ui.theme.screens.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val repository = AuthRepository()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBars = currentRoute != Screen.Login.route &&
            currentRoute != Screen.Signup.route

    Scaffold(

        topBar = {
            if (showBars) {
                TopAppBar(
                    title = {
                        Text("QuickAid")
                    }
                )
            }
        },

        bottomBar = {
            if (showBars) {

                NavigationBar {

                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        },
                        label = {
                            Text("Home")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Hospital.route,
                        onClick = {
                            navController.navigate(Screen.Hospital.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                        },
                        label = {
                            Text("Hospital")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.History.route,
                        onClick = {
                            navController.navigate(Screen.History.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        label = {
                            Text("History")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        label = {
                            Text("Profile")
                        }
                    )
                }
            }
        }

    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = if (repository.isLoggedIn())
                Screen.Home.route
            else
                Screen.Login.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Login.route) {

                LoginScreen(

                    onLoginClick = { email, password ->

                        CoroutineScope(Dispatchers.IO).launch {

                            try {

                                repository.login(email, password)

                                withContext(Dispatchers.Main) {

                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) {
                                            inclusive = true
                                        }
                                    }

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    },

                    onSignupClick = {
                        navController.navigate(Screen.Signup.route)
                    }
                )
            }

            composable(Screen.Signup.route) {

                SignupScreen(

                    onSignupClick = {
                        navController.popBackStack()
                    },

                    onLoginClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(Screen.Hospital.route) {
                HospitalScreen(navController)
            }

            composable(Screen.History.route) {
                HistoryScreen(navController)
            }


            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
        }
    }
}
