package com.coffee.shop.navigation

import androidx.annotation.DrawableRes
import com.cofee.shop.R

sealed class DashboardBottomNavigationDestinations(val route: String, @DrawableRes val icon: Int) {
    data object Home : DashboardBottomNavigationDestinations("home", R.drawable.ic_home)
    data object Favourites :
        DashboardBottomNavigationDestinations("favourites", R.drawable.ic_heart)

    data object Orders : DashboardBottomNavigationDestinations("orders", R.drawable.ic_bag)
    data object Notifications : DashboardBottomNavigationDestinations("notifications", R.drawable.ic_notification)
}