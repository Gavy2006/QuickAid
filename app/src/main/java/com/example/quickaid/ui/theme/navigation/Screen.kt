package com.example.quickaid.ui.theme.navigation


sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Signup : Screen("signup")

    object Home : Screen("home")

    object Hospital : Screen("hospital")

    object History : Screen("history")

    object Profile : Screen("profile")
}