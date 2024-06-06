package com.example.paxangaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.database.entities.AdminLoginEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viewmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viewmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController, adminLoginViwModel: AdminLoginViwModel,teamsViewModel: TeamsViewModel,matchViewModel: MatchViewModel,playerViewModel: PlayerViewModel,matchPlayerViewModel: MatchPlayerViewModel) {
    teamsViewModel.updateTeamCount()

    LaunchedEffect(key1 = true) {
        delay(100)
        navController.popBackStack() // Evitar volver a la Splash Screen

        if (teamsViewModel.teamCount < 5) {
            teamsViewModel.deleteAllTeams()
            matchViewModel.deleteAllMatches()
            playerViewModel.deleteAllPlayers()
            matchPlayerViewModel.deleteAllMatchPlayer()
            navController.navigate(Routes.FirstLogin.routes)
        }
        else{
            navController.navigate(Routes.TabRowMatchScreen.routes)
        }
    }


    adminLoginViwModel.insertUser(AdminLoginEntity(
        adminId = 1,
        userName = "admin",
        password = "1111"
    ))


    Splash()
}
@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.paxangapplogo),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp) // tamaño del logo
                .clip(CircleShape) // redondear la imagen
                .shadow(10.dp, CircleShape) // agregar sombra
                .background(Color.White) // fondo blanco para destacar el logo
                .padding(16.dp) // padding interno si es necesario
        )
    }
}