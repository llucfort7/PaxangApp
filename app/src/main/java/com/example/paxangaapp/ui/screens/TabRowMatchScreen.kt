package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabRowMatchScreen(
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    appViewModel: AppViewModel,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    matchViewModel.getAllMatches()
    val mMatches by matchViewModel.matchList.observeAsState(initial = emptyList())
    matchViewModel.getAllMatches()
    teamsViewModel.getAllTeams()
    val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
    teamsViewModel.getAllTeams()
//(Esta mal)Cambiar per un numero maxim de jornades afegides a la BD
    val tabTitles = (0..mMatches.size).map { "Tab $it" }
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary
                ),
                title = { Text(text = "PAXANGAPP") },
                actions = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.FormatListNumbered,
                                    contentDescription = "autor",
                                )
                            },
                            text = { "uno" },
                            onClick = { navController.navigate(Routes.ClassificationScreen.routes) }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.SportsSoccer,
                                    contentDescription = "autor",
                                )
                            },
                            text = { "DOS" },
                            onClick = { navController.navigate(Routes.PlayerClasScreen.routes) }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "autor",
                                )
                            },
                            text = { "DOS" },
                            onClick = { navController.navigate(Routes.NewPlayer.routes) }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AcUnit,
                                    contentDescription = "autor",
                                )
                            },
                            text = { "DOS" },
                            onClick = { navController.navigate(Routes.NewTeam.routes) }
                        )

                    }
                },
            )
        }
    ) {

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
            matchViewModel.getAllMatchesByNumMatch(selectedTabIndex)
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
                Text(text = matchEntity.matchId.toString())
                // Icono y nombre del equipo local
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Equipo 1",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    localTeam?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                // Resultado
                Text(
                    text = "${matchEntity.localGoals.toString()}-${matchEntity.vistGoals.toString()}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Icono y nombre del equipo visitante
                Row(verticalAlignment = Alignment.CenterVertically) {
                    visitorTeam?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.TimeToLeave,
                        contentDescription = "Equipo2",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


