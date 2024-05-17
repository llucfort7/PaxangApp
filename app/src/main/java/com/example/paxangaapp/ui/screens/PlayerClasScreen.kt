package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerTeamsViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerClasScreen(
    navController: NavHostController,
    playerViewModel:PlayerViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    // selectedTabIndex=oIndex/3
//(Esta mal)Cambiar per un numero maxim de jornades afegides a la BD
    val tabTitles = listOf("Goles", "Asist", "Amarillas", "Rojas")

    //val tabTitles = (0..nJourny).map { "Tab $it" }
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Clasificación") }, // Cambia el título
                actions = {
                },
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

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(57.dp))
            TabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            //matchViewModel.getAllMatchesByNumMatch(selectedTabIndex)

                        }
                    )

                }
            }
            when (selectedTabIndex) {
                0 -> GoalTab(playerViewModel,navController)
                1 -> AssistTab(playerViewModel,navController)
                2 -> YellowCardTab(playerViewModel,navController)
                3 -> RedCardTab(playerViewModel,navController)
            }
        }
    }
}

@Composable
fun GoalTab(playerViewModel:PlayerViewModel,navController: NavHostController, ) {
    playerViewModel.getAllPlayersByGoals()
    val players by playerViewModel.playerListGoal.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayersByGoals()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            items(players) { player ->
                PlayerRowA(
                    player, playerViewModel,navController,player.goalsP
                )
            }
        }
    }
}

@Composable
fun AssistTab(playerViewModel:PlayerViewModel,navController: NavHostController, ) {
    playerViewModel.getAllPlayersByAsisit()
    val players by playerViewModel.playerListAsisit.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayersByAsisit()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            items(players) { player ->
                PlayerRowA(
                    player, playerViewModel,navController,player.assistsP
                )
            }
        }
    }
}

@Composable
fun YellowCardTab(playerViewModel:PlayerViewModel,navController: NavHostController, ) {
    playerViewModel.getAllPlayersByYellowC()
    val players by playerViewModel.playerListYellowC.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayersByYellowC()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            items(players) { player ->
                PlayerRowA(
                    player, playerViewModel,navController,player.yellowCardsP
                )
            }
        }
    }
}

@Composable
fun RedCardTab(playerViewModel:PlayerViewModel,navController: NavHostController, ) {
    playerViewModel.getAllPlayersByRedC()
    val players by playerViewModel.playerListRedC.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayersByRedC()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            items(players) { player ->
                PlayerRowA(
                    player, playerViewModel,navController,player.redCardsP
                )
            }
        }
    }
}

// Función de utilidad para generar una lista de ejemplo



@Composable
fun PlayerRowA(
    player: PlayerEntity,
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
    int: Int
) {
    OutlinedCard(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { playerViewModel.onPlayerClicked(player)
                navController.navigate(Routes.PlayerInfoScreen.routes)
            },
        //elevation = 4
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(md_theme_light_primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Columna para el nombre del jugador y más información
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$int  ${player.playerName} ${player.playerSname}",
                    //style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Aquí puedes agregar más información del jugador si lo deseas,
                // como número de jugador, posición, etc.
            }
        }
    }
}
