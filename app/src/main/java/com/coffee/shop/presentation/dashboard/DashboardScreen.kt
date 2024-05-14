package com.coffee.shop.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cofee.shop.R
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.navigation.DashboardBottomNavigationDestinations
import com.coffee.shop.presentation.favorites.FavoritesScreen
import com.coffee.shop.presentation.home.HomeScreen
import com.coffee.shop.presentation.notifications.NotificationsScreen
import com.coffee.shop.presentation.orders.OrdersScreen
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, onCoffeeSelected: (DomainCoffee) -> Unit) {
    val bottomNavigationItems = listOf(
        DashboardBottomNavigationDestinations.Home,
        DashboardBottomNavigationDestinations.Favourites,
        DashboardBottomNavigationDestinations.Orders,
        DashboardBottomNavigationDestinations.Notifications
    )
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomNavigationItems.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = false,
                        icon = {
                            if (index == navigationSelectedItem) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = navigationItem.icon),
                                        contentDescription = navigationItem.icon.toString()
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_selected_nav),
                                        contentDescription = null
                                    )

                                }
                            } else {
                                Icon(
                                    painter = painterResource(id = navigationItem.icon),
                                    contentDescription = navigationItem.icon.toString()
                                )
                            }
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = DashboardBottomNavigationDestinations.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(DashboardBottomNavigationDestinations.Home.route) {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = appBgColor),
                    onCoffeeSelected = onCoffeeSelected
                )
            }
            composable(DashboardBottomNavigationDestinations.Favourites.route) {
                FavoritesScreen()
            }
            composable(DashboardBottomNavigationDestinations.Orders.route) {
                OrdersScreen(modifier = Modifier.fillMaxSize())
            }
            composable(DashboardBottomNavigationDestinations.Notifications.route) {
                NotificationsScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    CoffeeShopTheme {
        DashboardScreen(modifier = Modifier.fillMaxSize(), onCoffeeSelected = {})
    }
}