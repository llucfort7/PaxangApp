package com.example.paxangaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.AdminLoginEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.AdminLoginViwModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController, adminLoginViwModel: AdminLoginViwModel) {
    LaunchedEffect(key1 = true) {
        delay(100)
        navController.popBackStack() // Evitar volver a la Splash Screen
        navController.navigate(Routes.Onboarding.routes)
    }
//if (adminLoginViwModel.anyUserExists()){
    adminLoginViwModel.insertUser(AdminLoginEntity(
        "Admin",
        "1111"
    ))
    adminLoginViwModel.insertUser(AdminLoginEntity(
        "popo",
        "1111"
    ))
//}
    Splash()
}
@Composable
fun Splash() {

    Column(
        modifier = Modifier
            .background(color = md_theme_light_primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //Aplicar per a que es cambie al on boardingf o a la pantalla de admin
    }
}