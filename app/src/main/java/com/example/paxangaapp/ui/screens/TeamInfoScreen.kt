package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viewmodel.AppViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamInfoScreen(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
    matchViewModel: MatchViewModel,
    appViewModel: AppViewModel,
    playersViwModel: PlayerViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary
                ),
                title = { Text(text = "Info equipo") },
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Classificacion",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) {
        val team: TeamsEntity by teamsViewModel.selectedTeam.observeAsState(TeamsEntity())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
        val players by playersViwModel.playerListByTeam.observeAsState(emptyList())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
        teamsViewModel.getAllTeams()
        val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(4.dp),
            ) {
                team.clubImage?.let { it1 -> painterResource(it1) }?.let { it2 ->
                    Image(
                        painter = it2,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .clickable {
                                teamsViewModel.onTeamClicked(team)
                                navController.navigate(Routes.TeamInfoScreen.routes)
                            },
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Text(
                text = team.nameT,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            TeamDetailItem(label = "Nombre", value = team.nameT)
            TeamDetailItem(label = "Lugar ", value = team.localicacion)
            TeamDetailItem(label = "Puntos", value = team.points.toString())
            TeamDetailItem(label = "Partidos ganados ", value = team.winMatches.toString())
            TeamDetailItem(label = "Partidos empatados", value = team.tieMatches.toString())
            TeamDetailItem(label = "Partidos Perdidos", value = team.lostMatches.toString())
            TeamDetailItem(label = "Partidos jugados", value = team.playedMatches.toString())

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                items(players) { player ->
                    PlayerRow(player, playersViwModel, navController)
                }
            }
        }
    }
}

@Composable
fun TeamDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = value
        )
    }
}
