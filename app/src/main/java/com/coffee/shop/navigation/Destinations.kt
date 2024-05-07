package com.coffee.shop.navigation

sealed class Destinations(val route: String) {
    data object Splash : Destinations("splash")
    data object Introduction : Destinations("introduction")
    data object Register : Destinations("register")
    data object Login : Destinations("login")
    data object ForgotPassword : Destinations("forgot-password")
    data object Verification : Destinations("verification")
    data object Dashboard : Destinations("dashboard")
    data object Home : Destinations("home")
    data object Favourites : Destinations("favourites")
    data object Orders : Destinations("orders")
}