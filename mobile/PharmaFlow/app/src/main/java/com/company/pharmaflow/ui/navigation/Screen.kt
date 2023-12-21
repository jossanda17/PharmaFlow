package com.company.pharmaflow.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Product : Screen("product")
    object History : Screen("history")
    object Login : Screen("login")
}
