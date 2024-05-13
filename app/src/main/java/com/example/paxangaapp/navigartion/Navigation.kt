package com.example.paxangaapp.navigartion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paxangaapp.ui.screens.ClassificationScreen
import com.example.paxangaapp.ui.screens.MatchModifier
import com.example.paxangaapp.ui.screens.MatchPlayedModifier
import com.example.paxangaapp.ui.screens.NewPlayer
import com.example.paxangaapp.ui.screens.NewTeam
import com.example.paxangaapp.ui.screens.NoLeagueScreen
import com.example.paxangaapp.ui.screens.Onboarding
import com.example.paxangaapp.ui.screens.PlayerClasScreen
import com.example.paxangaapp.ui.screens.PlayerInfoScreen
import com.example.paxangaapp.ui.screens.PlayerMatchStats
import com.example.paxangaapp.ui.screens.SeeMatches
import com.example.paxangaapp.ui.screens.SplashScreen
import com.example.paxangaapp.ui.screens.TabRowMatchScreen
import com.example.paxangaapp.ui.screens.TeamInfoScreen
import com.example.paxangaapp.ui.viwmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerTeamsViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@Composable
fun Navigation(matchViewModel: MatchViewModel, teamsViewModel: TeamsViewModel, adminLoginViwModel: AdminLoginViwModel,playerViewModel: PlayerViewModel,matchPlayerViewModel: MatchPlayerViewModel,appViewModel: AppViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.routes,
    ){
        composable(Routes.SplashScreen.routes) {
            SplashScreen(navController,adminLoginViwModel)
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
            NewPlayer(navController,teamsViewModel, playerViewModel,matchViewModel, appViewModel)
        }
        composable(Routes.PlayerInfoScreen.routes) {
            PlayerInfoScreen( navController,playerViewModel)
        }
        composable(Routes.TeamInfoScreen.routes) {
            TeamInfoScreen( navController,teamsViewModel,matchViewModel,appViewModel,playerViewModel)
        }
        composable(Routes.TabRowMatchScreen.routes) {
            TabRowMatchScreen( navController,matchViewModel, teamsViewModel,appViewModel)
        }
        composable(Routes.SeeMatches.routes) {
            SeeMatches(navController,matchViewModel,teamsViewModel,playerViewModel,matchPlayerViewModel)
        }
        composable(Routes.NewTeam.routes) {
            NewTeam(teamsViewModel,navController,appViewModel)
        }
        composable(Routes.NoLeagueScreen.routes) {
            NoLeagueScreen(navController,teamsViewModel,appViewModel)
        }
        composable(Routes.MatchModifier.routes) {
            MatchModifier(navController,matchViewModel,teamsViewModel,appViewModel)
        }
        composable(Routes.MatchPlayedModifier.routes) {
            MatchPlayedModifier(matchViewModel,teamsViewModel,playerViewModel,matchPlayerViewModel,navController)
        }
        composable(Routes.PlayerMatchStats.routes) {
            PlayerMatchStats(matchPlayerViewModel,matchViewModel,playerViewModel,navController)
        }


    }
}