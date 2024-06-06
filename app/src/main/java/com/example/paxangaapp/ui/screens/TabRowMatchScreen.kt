package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viewmodel.AppViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabRowMatchScreen(
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    appViewModel: AppViewModel,
) {


    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.paxangapplogo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .width(60.dp) ,// Ajusta el tamaño del logo
                        tint = Color.Black // Define el color negro para este ícono

                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.ClassificationScreen.routes) }) {
                        Icon(
                            Icons.Default.FormatListNumbered, contentDescription = "Classification",
                            tint = Color.Black // Define el color negro para este ícono
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.PlayerClasScreen.routes) }) {
                        Icon(
                            Icons.Filled.SportsSoccer, contentDescription = "Edit",
                            tint = Color.Black // Define el color negro para este ícono
                        )
                    }
                   // Spacer(modifier = Modifier.weight(0.5f)) // Empuja el siguiente icono hacia la derecha
                    IconButton(onClick = { navController.navigate(Routes.Onboarding.routes) }) {
                        Icon(
                            Icons.Filled.Login, contentDescription = "Onboarding",
                            tint = Color.Black // Define el color negro para este ícono
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary // Cambia el color de fondo si es necesario
                )
            )
        }
    ) {
        matchViewModel.getNMtachesPlayed()
        var selectedTabIndex by remember { mutableStateOf(0) }
        matchViewModel.getAllMatches()
        teamsViewModel.getAllTeams()
        val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
        teamsViewModel.updateTeamCount()
        val nJourny = ((teamsViewModel.teamCount-1) * 2)
        val tabTitles = (1..nJourny).map { "J $it" }

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(57.dp))
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            matchViewModel.getAllMatchesByNumMatch(selectedTabIndex)
                        }
                    )
                }
            }
            matchViewModel.getAllMatchesByNumMatch(selectedTabIndex)
            val matches by matchViewModel.matchListNumber.observeAsState(initial = emptyList())
            //matchViewModel.getAllMatchesByNumMatch(selectedTabIndex)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {

                items(matches) { match ->
                    val localTeam = teams.firstOrNull { it.teamsId == match.localTeamId }
                    val visitorTeam = teams.firstOrNull { it.teamsId == match.visitorTeamId }
                    MatchRow(navController, match, matchViewModel, localTeam, visitorTeam)
                }
            }
        }

    }
}

@Composable
fun MatchRow(
    navController: NavHostController,
    matchEntity: MatchEntity,
    matchViewModel: MatchViewModel,
    localTeam: TeamsEntity?,
    visitorTeam: TeamsEntity?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                matchViewModel.onMatchCliked(matchEntity)
                navController.navigate(Routes.SeeMatches.routes)
            }
    ) {
        OutlinedCard(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),

            ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                    ) {
                        if (localTeam != null) {
                            localTeam.clubImage?.let { it1 -> painterResource(it1) }?.let { it2 ->
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
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    localTeam?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                if (matchEntity.isPlayed) {
                    Text(
                        text = "${matchEntity.localGoals.toString()}-${matchEntity.vistGoals.toString()}",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    Text(text = "(N/P)")
                }
                // Resultado


                // Icono y nombre del equipo visitante
                Row(verticalAlignment = Alignment.CenterVertically) {
                    visitorTeam?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        if (visitorTeam != null) {
                            visitorTeam.clubImage?.let { it1 -> painterResource(it1) }?.let { it2 ->
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
                    }
                }
            }
        }
    }
}