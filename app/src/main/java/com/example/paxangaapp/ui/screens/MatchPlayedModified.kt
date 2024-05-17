package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MatchPlayedModifier(
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchPlayerViewModel: MatchPlayerViewModel,
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                //  colors = TopAppBarDefaults.smallTopAppBarColors(
                //      containerColor = md_theme_light_primary
                //  ),
                title = { Text(text = "PAXANGAPP") },
                actions = {
                },
            )
        }

    ) {
        var showDialog by rememberSaveable { mutableStateOf(false) }

        //Objeto del actual partido

        val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(MatchEntity())
        //oldMatch.matchId?.let { it1 -> matchViewModel.getMatchById(it1) }
        //val match: MatchEntity by matchViewModel.selectedMatchId.observeAsState(MatchEntity())
        //oldMatch.matchId?.let { it1 -> matchViewModel.getMatchById(it1) }

        // matchI.matchId?.let { it1 -> matchViewModel.getMatchById(it1) }
        //val match:MatchEntity by matchViewModel.selectedMatchId.observeAsState(MatchEntity())

        //Numero de la jornada
        teamsViewModel.getAllTeams()
        //Le asignamos a la variable teams la lista teamlist del viewModel
        val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
        teamsViewModel.getAllTeams()

        //llamamos a la funcion del viewmodel de teams para saber asignar los equipos visitante y local ala variable
        teamsViewModel.getOneLTeam(match.localTeamId)
        teamsViewModel.getOneVTeam(match.visitorTeamId)

        //Le asignamos a la variable playeroal la lista matchPlayerListMatchGoal del viewModel
        match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatchGoal(it) }
        val playerGoal by matchPlayerViewModel.matchPlayerListMatchGoal.observeAsState(initial = emptyList())
        match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatchGoal(it) }


        // match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatch(it) }
        // val playerMatch by matchPlayerViewModel.matchPlayerListMatch.observeAsState(initial = emptyList())
        // match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatch(it) }


        playerViewModel.getAllPlayers()
        val players by playerViewModel.playerList.observeAsState(initial = emptyList())
        playerViewModel.getAllPlayers()


        // val teamWithMatch: TeamWithMach by teamMatchViewModel.selectedTeamWithMatch.observeAsState(TeamWithMach())
        val localTeam = teams.firstOrNull { it.teamsId == match.localTeamId }
        val visitorTeam = teams.firstOrNull { it.teamsId == match.visitorTeamId }
        playerViewModel.getPlayerByTeamIdLocal(match.localTeamId)
        val playersByIdLocal by playerViewModel.playerListByTeamLocal.observeAsState(emptyList())
        playerViewModel.getPlayerByTeamIdLocal(match.localTeamId)


        playerViewModel.getPlayerByTeamIdVisitor(match.visitorTeamId)
        val playersByIdVisitor by playerViewModel.playerListByTeamVisitor.observeAsState(emptyList())
        playerViewModel.getPlayerByTeamIdVisitor(match.visitorTeamId)

        match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }
        val matchPlayer by matchPlayerViewModel.matchPlayerListMatch.observeAsState(emptyList())
        match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .clip(RoundedCornerShape(15.dp)),
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 120.dp, end = 20.dp, start = 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White),
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Jornada ${match.matchNum}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Local Team
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            localTeam?.clubImage?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = "LocalTeam",
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                            localTeam?.nameT?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 8.dp),
                                )
                            }
                            if (playerGoal.isNotEmpty()) {
                                playerGoal.forEach { matchPlayer ->
                                    repeat(matchPlayer.goalsP) {
                                        val player =
                                            players.find { it.playersId == matchPlayer.playersId }
                                        if (player?.playerTeamID == localTeam?.teamsId) {
                                            if (player != null) {
                                                Text(
                                                    text = player.playerName,
                                                    //style = MaterialTheme.typography.body1,
                                                    modifier = Modifier.padding(top = 4.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Score
                        Text(
                            text = "${match.localGoals}-${match.vistGoals}",
                            //style = MaterialTheme.typography.headline4,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .background(Color(0xFF5C075F), shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                        // Visitor Team
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            visitorTeam?.clubImage?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = "visitorTeam",
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                            visitorTeam?.nameT?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 8.dp),
                                )
                            }
                            if (playerGoal.isNotEmpty()) {
                                playerGoal.forEach { matchPlayer ->
                                    repeat(matchPlayer.goalsP) {
                                        val player =
                                            players.find { it.playersId == matchPlayer.playersId }
                                        if (player?.playerTeamID == visitorTeam?.teamsId) {
                                            if (player != null) {
                                                Text(
                                                    text = player.playerName,
                                                    //style = MaterialTheme.typography.body1,
                                                    modifier = Modifier.padding(top = 4.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .clip(RoundedCornerShape(15.dp))
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 120.dp, end = 20.dp, start = 20.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White),
                        shape = RectangleShape
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Jornada ${match.matchNum}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 10.dp),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Primera LazyColumn

                                LazyColumn(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(vertical = 8.dp)
                                        .fillMaxHeight()
                                ) {
                                    items(playersByIdLocal) { player ->
                                        PlayerRowMod(
                                            player,
                                            match,
                                            playerViewModel,
                                            navController,
                                            matchViewModel,
                                            matchPlayer

                                        )
                                    }
                                }
                                LazyColumn(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(vertical = 8.dp)
                                        .fillMaxHeight()
                                ) {
                                    items(playersByIdVisitor) { player ->
                                        PlayerRowMod(
                                            player,
                                            match,
                                            playerViewModel,
                                            navController,
                                            matchViewModel,
                                            matchPlayer
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) {
                Text("Confirmar")
            }

            if (showDialog) {
                showDialog = visitorTeam?.let { it1 ->
                    localTeam?.let { it2 ->
                        showConfirmationDialog(
                            navController, match, matchViewModel, teamsViewModel,
                            it2,
                            it1
                        )
                    }
                }!!
            }
        }

    }
}

@Composable
fun PlayerRowMod(
    player: PlayerEntity,
    matchEntity: MatchEntity,
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    listMatchPlayer: List<MatchPlayerRelationEntity>
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                var isPlayed=true
                for (i in 0..<listMatchPlayer.size){
                    if (listMatchPlayer[i].playersId==player.playersId&&listMatchPlayer[i].isPayed){
                        isPlayed=false
                    }
                }
                if (isPlayed){
                    playerViewModel.onPlayerClicked(player)
                    matchViewModel.onMatchCliked(matchEntity)
                    navController.navigate(Routes.PlayerMatchStats.routes)
                }

            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(md_theme_light_primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${player.playersId} ${player.playerName} ${player.playerSname}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

        }
    }
}

@Composable
fun showConfirmationDialog(
    navController: NavHostController,
    matchEntity: MatchEntity,
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    teamsEntityLocal: TeamsEntity,
    teamsEntityVisitor: TeamsEntity
): Boolean {
    // Variable para controlar si el diálogo está visible
    var showDialog by rememberSaveable { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar partido") },
            text = { Text("Si confirmas no podras modificar el partido") },
            confirmButton = {
                Button(
                    onClick = {
                        matchViewModel.modificateMatch(
                            MatchEntity(
                                matchId = matchEntity.matchId,
                                date = matchEntity.date,
                                matchNum = matchEntity.matchNum,
                                vistGoals = matchEntity.vistGoals,
                                localTeamId = matchEntity.localTeamId,
                                visitorTeamId = matchEntity.visitorTeamId,
                                localGoals = matchEntity.localGoals,
                                isPlayed = true
                            )
                        )
                        //The Local Win and the Vist Lost
                        if (matchEntity.localGoals > matchEntity.vistGoals) {
                            // Local team data modificate if local wins
                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityLocal.teamsId,
                                    clubImage = teamsEntityLocal.clubImage,
                                    localicacion = teamsEntityLocal.localicacion,
                                    nameT = teamsEntityLocal.nameT,
                                    points = teamsEntityLocal.points + 3,
                                    winMatches = teamsEntityLocal.winMatches + 1,
                                    tieMatches = teamsEntityLocal.tieMatches,
                                    lostMatches = teamsEntityLocal.lostMatches,
                                    playedMatches = teamsEntityLocal.playedMatches + 1
                                )
                            )
                            // Visit team data modificate if local wins

                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityVisitor.teamsId,
                                    clubImage = teamsEntityVisitor.clubImage,
                                    localicacion = teamsEntityVisitor.localicacion,
                                    nameT = teamsEntityVisitor.nameT,
                                    points = teamsEntityVisitor.points,
                                    winMatches = teamsEntityVisitor.winMatches,
                                    tieMatches = teamsEntityVisitor.tieMatches,
                                    lostMatches = teamsEntityVisitor.lostMatches + 1,
                                    playedMatches = teamsEntityVisitor.playedMatches + 1
                                )
                            )
                        }
                        //Local lost and Visit wins
                        if (matchEntity.vistGoals > matchEntity.localGoals) {
                            // Local team data modificate if local lost

                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityLocal.teamsId,
                                    clubImage = teamsEntityLocal.clubImage,
                                    localicacion = teamsEntityLocal.localicacion,
                                    nameT = teamsEntityLocal.nameT,
                                    points = teamsEntityLocal.points,
                                    winMatches = teamsEntityLocal.winMatches,
                                    tieMatches = teamsEntityLocal.tieMatches,
                                    lostMatches = teamsEntityLocal.lostMatches + 1,
                                    playedMatches = teamsEntityLocal.playedMatches + 1
                                )
                            )
                            // Visit team data modificate if local lost

                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityVisitor.teamsId,
                                    clubImage = teamsEntityVisitor.clubImage,
                                    localicacion = teamsEntityVisitor.localicacion,
                                    nameT = teamsEntityVisitor.nameT,
                                    points = teamsEntityVisitor.points + 3,
                                    winMatches = teamsEntityVisitor.winMatches + 1,
                                    tieMatches = teamsEntityVisitor.tieMatches,
                                    lostMatches = teamsEntityVisitor.lostMatches,
                                    playedMatches = teamsEntityVisitor.playedMatches + 1

                                )
                            )
                        }
                        if (matchEntity.vistGoals == matchEntity.localGoals) {
//tie
                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityLocal.teamsId,
                                    clubImage = teamsEntityLocal.clubImage,
                                    localicacion = teamsEntityLocal.localicacion,
                                    nameT = teamsEntityLocal.nameT,
                                    points = teamsEntityLocal.points + 1,
                                    winMatches = teamsEntityLocal.winMatches,
                                    tieMatches = teamsEntityLocal.tieMatches + 1,
                                    lostMatches = teamsEntityLocal.lostMatches,
                                    playedMatches = teamsEntityLocal.playedMatches + 1
                                )
                            )
                            // Visit team data modificate if local tie

                            teamsViewModel.updateTeam(
                                TeamsEntity(
                                    teamsId = teamsEntityVisitor.teamsId,
                                    clubImage = teamsEntityVisitor.clubImage,
                                    localicacion = teamsEntityVisitor.localicacion,
                                    nameT = teamsEntityVisitor.nameT,
                                    points = teamsEntityVisitor.points + 1,
                                    winMatches = teamsEntityVisitor.winMatches,
                                    tieMatches = teamsEntityVisitor.tieMatches + 1,
                                    lostMatches = teamsEntityVisitor.lostMatches,
                                    playedMatches = teamsEntityVisitor.playedMatches + 1

                                )
                            )
                        }

                        navController.popBackStack()

                        showDialog = false
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")

                }
            }
        )
    }
    return showDialog
}
