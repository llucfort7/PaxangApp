package com.example.paxangaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(5000)
        navController.popBackStack() // Evitar volver a la Splash Screen
        navController.navigate(Routes.Onboarding.routes)
    }

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
        Image(
            painter = painterResource(id = R.drawable.paxangapplogo),
            contentDescription = "",
            Modifier.size(200.dp, 200.dp),
        )
        Text("PaxangaApp")
    }
}