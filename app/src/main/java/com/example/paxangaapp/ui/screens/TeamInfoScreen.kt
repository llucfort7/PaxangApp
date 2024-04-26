package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.PlayerTeamsViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamInfoScreen(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
    playersViwModel: PlayerViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary
                ),
                title = { Text(text = "Info equipo") },
                actions = {


                },
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
      //  playersViwModel.addPlayer(PlayerEntity(
      //      playerTeamID=1,
      //      playerNumber = 10,
      //      playerName = "Lionel",
      //      playerSname = "Messi",
      //      goodFoot = "Left",
      //      position = "Forward",
      //      goalsP = 700,
      //      foulsP = 100,
      //      assistsP = 300,
      //      yellowCardsP = 20,
      //      redCardsP = 2
      //  ))
      //  playersViwModel.addPlayer(PlayerEntity(
      //      playerTeamID=1,
      //      playerNumber = 6,
      //      playerName = "Pedro",
      //      playerSname = "Porro",
      //      goodFoot = "Left",
      //      position = "Forward",
      //      goalsP = 700,
      //      foulsP = 100,
      //      assistsP = 300,
      //      yellowCardsP = 20,
      //      redCardsP = 2
      //  ))
      //  playersViwModel.addPlayer(PlayerEntity(
      //      playerTeamID=1,
      //      playerNumber = 7,
      //      playerName = "Xavi",
      //      playerSname = "Hernandez",
      //      goodFoot = "Left",
      //      position = "Forward",
      //      goalsP = 700,
      //      foulsP = 100,
      //      assistsP = 300,
      //      yellowCardsP = 20,
      //      redCardsP = 2
      //  ))

        //teamsEntity.teamsId?.let { it1 -> playerTeamsViewModel.getAllTeamsWithPlayersSameTeam(it1) }
        //val playerAndTeam by playerTeamsViewModel.teamList.observeAsState(emptyList())
        //teamsEntity.teamsId?.let { it1 -> playerTeamsViewModel.getAllTeamsWithPlayersSameTeam(it1) }
        val team: TeamsEntity by teamsViewModel.selectedTeam.observeAsState(TeamsEntity())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
        val players by playersViwModel.playerListByTeam.observeAsState(emptyList())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = team.nameT,
                //style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PlayerDetailItem(
                label = "Team id",
                value = team.teamsId.toString()
            )
            PlayerDetailItem(
                label = "Localiacion",
                value = team.localicacion
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                //  item {
                //      // Spacer para dejar espacio para la TopAppBar
                //      Spacer(modifier = Modifier.height(256.dp))
                //  }

                items(players) { player ->
                    PlayerRow(
                        player, playersViwModel, navController
                    )
                }
            }
        }
    }
}


@Composable
fun TeamDetailInfo(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            // style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = value,
            // style = MaterialTheme.typography.body1,
        )
    }
}

