package com.example.paxangaapp.navigartion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paxangaapp.ui.screens.MatchScreen
import com.example.paxangaapp.ui.screens.Onboarding
import com.example.paxangaapp.ui.screens.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.routes,
    ){
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navController)
        }
        composable(Routes.MatchScreen.routes) {
            MatchScreen()
        }
        composable(Routes.Onboarding.routes) {
            Onboarding(navController)
        }

    }

}