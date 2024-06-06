package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationScreen(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Clasificación") },
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) {
        // Obtén la lista de equipos
        teamsViewModel.getAllTeams()
        val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())

        // Tabla de clasificación
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(76.dp))
            }

            // Encabezados de columna
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "PS",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text = "T",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text = "PJ", // Partidos jugados
                        modifier = Modifier.padding(start = 4.dp, end = 10.dp)
                    )
                    Text(
                        text = "PG", // Partidos ganados
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "PE", // Partidos empatados
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "PP", // Partidos perdidos
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Pts", // Puntos
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }

            // Datos de los equipos
            items(teams) { team ->
                val index = teams.indexOf(team) + 1
                ClassificationRow(team, index, navController, teamsViewModel)
            }
        }
    }
}

@Composable
fun ClassificationRow(
    team: TeamsEntity,
    position: Int,
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
) {
    var color = Color.White

    if (position == 1) {
        color = Color.Green
    }
   // var leterColor = Color.B  lack


    // Aplica una forma redondeada al contenedor del Row
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
            .background(color, shape = RoundedCornerShape(8.dp))
            .clickable {
                teamsViewModel.onTeamClicked(team)
                navController.navigate(Routes.TeamInfoScreen.routes)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Posición
            Text(
                text = position.toString(),
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)
            )
            // Equipo
            Box(modifier = Modifier
            .height(35.dp)
                .width(35.dp)
            ){
                team.clubImage?.let { it1 -> painterResource(it1) }?.let { it2 ->
                    Image(
                        painter = it2,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        contentScale = ContentScale.Fit // Cambia la escala de contenido a Fit
                    )
                }
            }
            Text(
                text = team.nameT,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)

            )
            // Partidos jugados
            Text(
                text = team.playedMatches.toString(),
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Partidos ganados
            Text(
                text = team.winMatches.toString(),
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Partidos empatados
            Text(
                text = team.tieMatches.toString(),
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Partidos perdidos
            Text(
                text = team.lostMatches.toString(),
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Puntos
            Text(
                text = team.points.toString(),
                color = Color.Black,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}
