package com.coffee.shop.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.coffee.shop.navigation.AppUri.uri
import com.coffee.shop.presentation.authentication.forgotpassword.ForgotPasswordScreen
import com.coffee.shop.presentation.authentication.login.LoginScreen
import com.coffee.shop.presentation.authentication.register.RegisterScreen
import com.coffee.shop.presentation.authentication.verification.VerificationScreen
import com.coffee.shop.presentation.dashboard.DashboardScreen
import com.coffee.shop.presentation.introduction.IntroductionScreen
import com.coffee.shop.presentation.splash.SplashScreen

object AppUri {
    const val uri = "https://cofeeshop.com"
}

@Composable
fun CofeeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Splash.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "$uri/$startDestination"
    ) {
        splashGraph(navController, "$uri/${Destinations.Splash.route}")
        loginGraph(navController, "$uri/${Destinations.Login.route}")
        registerGraph(navController, "$uri/${Destinations.Register.route}")
        forgotPasswordGraph(navController, "$uri/${Destinations.ForgotPassword.route}")
        verificationGraph(navController, "$uri/${Destinations.Verification.route}")
        introductionGraph(navController, "$uri/${Destinations.Introduction.route}")
        dashboardGraph("$uri/${Destinations.Dashboard.route}")
    }
}

fun NavGraphBuilder.splashGraph(navController: NavController, route: String) {
    composable(
        route = route, deepLinks = listOf(navDeepLink { uriPattern = Destinations.Splash.route })
    ) {
        SplashScreen(
            modifier = Modifier.fillMaxSize(),
            onTimeout = {
                navController.navigate(route = "$uri/$it") {
                    popUpTo("$uri/${Destinations.Splash.route}") {
                        inclusive = true
                    }
                }
            },
        )
    }
}

fun NavGraphBuilder.loginGraph(navController: NavController, route: String) {
    composable(
        route = route, deepLinks = listOf(navDeepLink { uriPattern = Destinations.Login.route })
    ) {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            onForgotPasswordTap = {
                navController.navigate(route = "$uri/${Destinations.ForgotPassword.route}")
            },
            onLoginSuccess = {
                navController.navigate(route = "$uri/${Destinations.Dashboard.route}") {
                    popUpTo("$uri/${Destinations.Login.route}") {
                        inclusive = true
                    }
                }
            },
            onRegisterTap = {
                navController.navigate(route = "$uri/${Destinations.Register.route}")
            },
        )
    }
}

fun NavGraphBuilder.registerGraph(navController: NavController, route: String) {
    composable(
        route = route, deepLinks = listOf(navDeepLink { uriPattern = Destinations.Register.route })
    ) {
        RegisterScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onRegisterSuccess = {
                navController.navigateUp()
            },
            onLoginTap = {
                navController.navigateUp()
            },
            onTermsTap = {},
        )
    }
}

fun NavGraphBuilder.forgotPasswordGraph(navController: NavController, route: String) {
    composable(
        route = route,
        deepLinks = listOf(navDeepLink { uriPattern = Destinations.ForgotPassword.route })
    ) {
        ForgotPasswordScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onEmailSent = {
                navController.navigate(route = "$uri/${Destinations.Verification.route}")
            },
        )
    }
}

fun NavGraphBuilder.verificationGraph(navController: NavController, route: String) {
    composable(
        route = route,
        deepLinks = listOf(navDeepLink { uriPattern = Destinations.Verification.route })
    ) {
        VerificationScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onOTPVerified = {
                navController.navigate(route = "$uri/${Destinations.Login.route}") {
                    popUpTo("$uri/${Destinations.Login.route}") {
                        inclusive = true
                    }
                }
            },
        )
    }
}

fun NavGraphBuilder.introductionGraph(navController: NavController, route: String) {
    composable(
        route = route,
        deepLinks = listOf(navDeepLink { uriPattern = Destinations.Introduction.route })
    ) {
        IntroductionScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onGetStarted = {
                navController.navigate(route = "$uri/${Destinations.Login.route}") {
                    popUpTo("$uri/${Destinations.Introduction.route}") {
                        inclusive = true
                    }
                }
            },
        )
    }
}

fun NavGraphBuilder.dashboardGraph(route: String) {
    composable(
        route = route, deepLinks = listOf(navDeepLink { uriPattern = Destinations.Dashboard.route })
    ) {
        DashboardScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}