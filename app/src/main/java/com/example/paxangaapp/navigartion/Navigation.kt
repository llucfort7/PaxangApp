package com.example.paxangaapp.navigartion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paxangaapp.ui.screens.ClassificationScreen
import com.example.paxangaapp.ui.screens.MatchScreen
import com.example.paxangaapp.ui.screens.Onboarding
import com.example.paxangaapp.ui.screens.SplashScreen
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@Composable
fun Navigation(teamsViewModel: TeamsViewModel,loginViwModel: LoginViwModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.routes,
    ){
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navController,loginViwModel)
        }
        composable(Routes.MatchScreen.routes) {
            MatchScreen(navController)
        }
        composable(Routes.Onboarding.routes) {
            Onboarding(navController,teamsViewModel,loginViwModel)
        }
        composable(Routes.ClassificationScreen.routes) {
            ClassificationScreen(navController,teamsViewModel)
        }

    }

}