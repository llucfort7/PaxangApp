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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viewmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeMatches(
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchPlayerViewModel: MatchPlayerViewModel,

    ) {
    //Top App Bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "PAXANGAPP") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(
                MatchEntity()
            )
            Spacer(modifier = Modifier.height(65.dp))
            TopBarComponent(
                matchViewModel,
                teamsViewModel,
                playerViewModel,
                matchPlayerViewModel,
                navController
            )
            if (match.isPlayed) {
                playerViewModel.getAllPlayers()
                val players by playerViewModel.playerList.observeAsState(initial = emptyList())
                playerViewModel.getAllPlayers()
                match.matchId?.let { it1 ->
                    matchPlayerViewModel.getAllMatchPlayersByMatch(
                        it1
                    )
                }
                val stats by matchPlayerViewModel.matchPlayerListMatch.observeAsState(
                    emptyList()
                )
                match.matchId?.let { it1 ->
                    matchPlayerViewModel.getAllMatchPlayersByMatch(
                        it1
                    )
                }
                MatchStats(stats, match, players)
                //
                ManagerComments(matchEntity = match)
                //llamamos a la funcion del viewmodel de teams para saber asignar los equipos visitante y local ala variable
                teamsViewModel.getAllTeams()
                //Le asignamos a la variable teams la lista teamlist del viewModel
                val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
                teamsViewModel.getAllTeams()

                teamsViewModel.getOneLTeam(match.localTeamId)
                teamsViewModel.getOneVTeam(match.visitorTeamId)

                playerViewModel.getPlayerByTeamIdLocal(match.localTeamId)
                val playersByIdLocal by playerViewModel.playerListByTeamLocal.observeAsState(
                    emptyList()
                )
                playerViewModel.getPlayerByTeamIdLocal(match.localTeamId)

                playerViewModel.getPlayerByTeamIdVisitor(match.visitorTeamId)
                val playersByIdVisitor by playerViewModel.playerListByTeamVisitor.observeAsState(
                    emptyList()
                )
                playerViewModel.getPlayerByTeamIdVisitor(match.visitorTeamId)

                match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }
                val matchPlayer by matchPlayerViewModel.matchPlayerListMatch.observeAsState(
                    emptyList()
                )
                match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }

                var playersPlayedInMatchL = mutableListOf<PlayerEntity>()
                for (i in 0..<playersByIdLocal.size) {
                    for (x in 0..<matchPlayer.size) {
                        if (playersByIdLocal[i].playersId == matchPlayer[x].playersId) {
                            playersPlayedInMatchL.add(playersByIdLocal[i])
                        }
                    }
                }
                var playersPlayedInMatchV = mutableListOf<PlayerEntity>()
                for (i in 0..<playersByIdVisitor.size) {
                    for (x in 0..<matchPlayer.size) {
                        if (playersByIdVisitor[i].playersId == matchPlayer[x].playersId) {
                            playersPlayedInMatchV.add(playersByIdVisitor[i])
                        }
                    }
                }
                PlayedPlayers(
                    match,
                    playerViewModel,
                    matchViewModel,
                    playersPlayedInMatchL,
                    playersPlayedInMatchV,
                    matchPlayer,
                    navController
                )

            }else{
                Column (
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(text = "Partido a la espera de ser jugado", textAlign = TextAlign.Center)
                }
            }
        }
    }
}


@Composable
fun TopBarComponent(
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchPlayerViewModel: MatchPlayerViewModel,
    navController: NavHostController,

    ) {
    // Objeto del actual partido
    val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(MatchEntity())

    // Numero de la jornada
    teamsViewModel.getAllTeams()
    // Le asignamos a la variable teams la lista teamlist del viewModel
    val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
    teamsViewModel.getAllTeams()

    // Llamamos a la función del viewmodel de teams para saber asignar los equipos visitante y local a la variable
    teamsViewModel.getOneLTeam(match.localTeamId)
    teamsViewModel.getOneVTeam(match.visitorTeamId)

    // Le asignamos a la variable playeroal la lista matchPlayerListMatchGoal del viewModel
    match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatchGoal(it) }
    val playerGoal by matchPlayerViewModel.matchPlayerListMatchGoal.observeAsState(initial = emptyList())
    match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatchGoal(it) }

    playerViewModel.getAllPlayers()
    val players by playerViewModel.playerList.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayers()

    val localTeam = teams.firstOrNull { it.teamsId == match.localTeamId }
    val visitorTeam = teams.firstOrNull { it.teamsId == match.visitorTeamId }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Jornada ${match.matchNum}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    // Primera columna (equipo local)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (localTeam != null) {
                            Image(
                                painter = painterResource(id = localTeam.clubImage!!),
                                contentDescription = "LocalTeam",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clickable {
                                        teamsViewModel.onTeamClicked(localTeam)
                                        navController.navigate(Routes.TeamInfoScreen.routes)
                                    }
                            )
                            Text(
                                text = localTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 10.dp),
                            )
                        }

                        if (playerGoal.isNotEmpty()) {
                            for (i in 0 until playerGoal.size) {
                                for (x in 0 until playerGoal[i].goalsP) {
                                    for (z in 0 until players.size) {
                                        if (players[z].playersId == playerGoal[i].playersId) {
                                            if (localTeam != null) {
                                                if (players[z].playerTeamID == localTeam.teamsId) {
                                                    Text(text = players[z].playerName)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Segunda columna (resultado)
                    Column(
                        Modifier.padding(25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${match.localGoals}-${match.vistGoals}",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.background(
                                Color(0xFF5C075F),
                                shape = RoundedCornerShape(10.dp)
                            )
                        )
                    }

                    // Tercera columna (equipo visitante)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.End

                    ) {
                        if (visitorTeam != null) {
                            Image(
                                painter = painterResource(id = visitorTeam.clubImage!!),
                                contentDescription = "visitorTeam",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clickable {
                                        teamsViewModel.onTeamClicked(visitorTeam)
                                        navController.navigate(Routes.TeamInfoScreen.routes)
                                    }
                            )
                            Text(
                                text = visitorTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        if (playerGoal.isNotEmpty()) {
                            for (i in 0 until playerGoal.size) {
                                for (x in 0 until playerGoal[i].goalsP) {
                                    for (z in 0 until players.size) {
                                        if (players[z].playersId == playerGoal[i].playersId) {
                                            if (visitorTeam != null) {
                                                if (players[z].playerTeamID == visitorTeam.teamsId) {
                                                    Text(text = players[z].playerName)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "Datos del partido",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun MatchStats(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Card(
            modifier = Modifier

        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),

                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    Text(
                        text = makeStatsFaultsL(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Faltas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = makeStatsFaultsV(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LinearProgressIndicator(progress = 1f)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    Text(
                        text = makeStatsYellowCardsL(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Amarillas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = makeStatsYellowCardsV(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LinearProgressIndicator(progress = 1f)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    Text(
                        text = makeStatsRedCardsL(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Rojas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = makeStatsRedCardsV(list, matchEntity, listP).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LinearProgressIndicator(progress = 1f)
                }
            }

        }
    }
}

@Composable
fun PlayedPlayers(
    match: MatchEntity,
    playerViewModel: PlayerViewModel,
    matchViewModel: MatchViewModel,
    playersByIdLocal: List<PlayerEntity>,
    playersByIdVisitor: List<PlayerEntity>,
    listMatchPlayer: List<MatchPlayerRelationEntity>,
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(horizontal = 10.dp, vertical = 10.dp)

    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
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
                    text = "Jugadores",
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
                            PlayerRowPlayed(
                                player,
                                match,
                                playerViewModel,
                                navController,
                                matchViewModel,
                                listMatchPlayer
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
                            PlayerRowPlayed(
                                player,
                                match,
                                playerViewModel,
                                navController,
                                matchViewModel,
                                listMatchPlayer
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun PlayerRowPlayed(
    player: PlayerEntity,
    matchEntity: MatchEntity,
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    listMatchPlayer: List<MatchPlayerRelationEntity>
) {
    var seePlayer by rememberSaveable { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                for (i in listMatchPlayer.indices) {
                    if (listMatchPlayer[i].playersId == player.playersId && listMatchPlayer[i].isPayed) {
                        seePlayer = true
                    }
                }
                playerViewModel.onPlayerClicked(player)
                matchViewModel.onMatchCliked(matchEntity)
            }
    ) {
        if (seePlayer) {
            seePlayer = playerMatchStats(playerViewModel, listMatchPlayer, navController)
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${player.playerSname}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}


fun makeStatsFaultsL(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.localTeamId) {
                cnt += list[i].foulsP
            }

        }
    }
    return cnt
}


fun makeStatsFaultsV(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.visitorTeamId) {
                cnt += list[i].foulsP
            }

        }
    }
    return cnt
}

fun makeStatsYellowCardsL(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.localTeamId) {
                cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}

fun makeStatsYellowCardsV(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.visitorTeamId) {
                cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}

fun makeStatsRedCardsL(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.localTeamId) {
                cnt += list[i].redCardsP
            }

        }
    }
    return cnt
}

fun makeStatsRedCardsV(
    list: List<MatchPlayerRelationEntity>,
    matchEntity: MatchEntity,
    listP: List<PlayerEntity>
): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices) {
            if (list[i].playersId == listP[z].playersId && listP[z].playerTeamID == matchEntity.visitorTeamId) {
                cnt += list[i].redCardsP
            }

        }
    }
    return cnt
}

@Composable
fun playerMatchStats(
    playerViewModel: PlayerViewModel,
    listMatchPlayer: List<MatchPlayerRelationEntity>,
    navController: NavHostController
): Boolean {
    // Variable para controlar si el diálogo está visible
    var showDialog by rememberSaveable { mutableStateOf(true) }
    var playerMatch = MatchPlayerRelationEntity(0, 0)
    val player: PlayerEntity by playerViewModel.selectedPlayer.observeAsState(PlayerEntity())

    for (i in listMatchPlayer.indices) {
        if (listMatchPlayer[i].playersId == player.playersId) {
            playerMatch = listMatchPlayer[i]
        }
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier

                .padding(10.dp),
            onDismissRequest = { showDialog = false },
            title = { Text(" ${player.playerName} ${player.playerSname}") },
            text = {
                Column {
                    Text("Stats:")
                    Text(text = "Goles: ${playerMatch.goalsP}")
                    Text(text = "Asistencias: ${playerMatch.assistsP}")
                    Text(text = "Faltas: ${playerMatch.foulsP}")
                    Text(text = "Amarillas: ${playerMatch.yellowCardsP}")
                    Text(text = "Rojas: ${playerMatch.redCardsP}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        navController.navigate(Routes.PlayerInfoScreen.routes)
                        showDialog = false
                    }
                ) {
                    Text("Ver al jugador")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Volver")
                }
            }
        )
    }
    return showDialog
}

@Composable
fun ManagerComments(matchEntity: MatchEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Comentarios del partido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = matchEntity.comments,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
