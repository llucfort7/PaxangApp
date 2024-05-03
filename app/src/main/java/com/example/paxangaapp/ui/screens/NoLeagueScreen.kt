package com.example.paxangaapp.ui.screens

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerTeamsViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@Composable
fun NoLeagueScreen(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
    appViewModel: AppViewModel
) {
    var teamsNumber by rememberSaveable { mutableStateOf(3) }

    teamsViewModel.updateTeamCount()
    if (teamsViewModel.teamCount < 1) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally


        ) {
            Row(Modifier.padding(10.dp)) {
                Text(text = "No hay ninguna liga iniciada")
            }

            Row(Modifier.padding(3.dp)) {
                Text(text = "Numero de equipos")
            }
            AndroidView(
                modifier = Modifier.width(50.dp),
                factory = { context ->
                    NumberPicker(context).apply {
                        setOnValueChangedListener { _, _, newval ->
                            teamsNumber = newval
                        }
                        minValue = 3
                        maxValue = 10
                        value = teamsNumber // Esto asegura que el NumberPicker muestre el valor actual de teamsNumber
                    }
                }
            )
            Button(onClick = {
                appViewModel.numTeamsChangue(teamsNumber)
                navController.navigate(Routes.NewTeam.routes)

            }) {
                Text(text = "Crear una liga")
            }
        }
    } else {
        navController.navigate(Routes.TabRowMatchScreen.routes)
    }
}
