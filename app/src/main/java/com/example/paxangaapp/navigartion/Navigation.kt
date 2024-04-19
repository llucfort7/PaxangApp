package com.example.paxangaapp.navigartion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paxangaapp.ui.screens.ClassificationScreen
import com.example.paxangaapp.ui.screens.MatchScreen
import com.example.paxangaapp.ui.screens.NewPlayer
import com.example.paxangaapp.ui.screens.Onboarding
import com.example.paxangaapp.ui.screens.PlayerClasScreen
import com.example.paxangaapp.ui.screens.PlayerInfoScreen
import com.example.paxangaapp.ui.screens.SplashScreen
import com.example.paxangaapp.ui.screens.TabRowMatchScreen
import com.example.paxangaapp.ui.screens.TeamInfoScreen
import com.example.paxangaapp.ui.viwmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerTeamsViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@Composable
fun Navigation(matchViewModel: MatchViewModel, teamsViewModel: TeamsViewModel, adminLoginViwModel: AdminLoginViwModel,playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.routes,
    ){
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navController,adminLoginViwModel)
        }
        composable(Routes.MatchScreen.routes) {
            MatchScreen(navController,matchViewModel,teamsViewModel)
        }
        composable(Routes.Onboarding.routes) {
            Onboarding(navController,teamsViewModel,adminLoginViwModel)
        }
        composable(Routes.ClassificationScreen.routes) {
            ClassificationScreen(navController,teamsViewModel)
        }
        composable(Routes.PlayerClasScreen.routes) {
            PlayerClasScreen(navController, playerViewModel)
        }
        composable(Routes.NewPlayer.routes) {
            NewPlayer(navController, playerViewModel)
        }
        composable(Routes.PlayerInfoScreen.routes) {
            PlayerInfoScreen( navController,playerViewModel)
        }
        composable(Routes.TeamInfoScreen.routes) {
            TeamInfoScreen( navController,teamsViewModel,playerViewModel)
        }
        composable(Routes.TabRowMatchScreen.routes) {
            TabRowMatchScreen( navController,matchViewModel, teamsViewModel)
        }
    }
}