package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamMatchRelationEntity
import com.example.paxangaapp.database.entities.TeamWithMach
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamMatchViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

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
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary
                ),
                title = { Text(text = "PAXANGAPP") },
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
        val scrollState = rememberScrollState()
        Scaffold(
            topBar = {},
            bottomBar = {},
            content = {
                Surface(
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(scrollState)
                ) {
                    TopBarComponent(
                        matchViewModel,
                        teamsViewModel,
                        playerViewModel,
                        matchPlayerViewModel
                    )
                    val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(
                        MatchEntity()
                    )
                    playerViewModel.getAllPlayers()
                    val players by playerViewModel.playerList.observeAsState(initial = emptyList())
                    playerViewModel.getAllPlayers()
                    match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }
                    val stats by matchPlayerViewModel.matchPlayerListMatch.observeAsState(emptyList())
                    match.matchId?.let { it1 -> matchPlayerViewModel.getAllMatchPlayersByMatch(it1) }
                    MatchStats(stats, match,players)

                }
            }
        )
    }
}

@Composable
fun TopBarComponent(
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchPlayerViewModel: MatchPlayerViewModel,
) {


    //Objeto del actual partido
    val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(MatchEntity())


    //Numero de la jornada
    val num = match.matchId
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

    playerViewModel.getAllPlayers()
    val players by playerViewModel.playerList.observeAsState(initial = emptyList())
    playerViewModel.getAllPlayers()


    // val teamWithMatch: TeamWithMach by teamMatchViewModel.selectedTeamWithMatch.observeAsState(TeamWithMach())
    val localTeam = teams.firstOrNull { it.teamsId == match.localTeamId }
    val visitorTeam = teams.firstOrNull { it.teamsId == match.visitorTeamId }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .padding(top = 75.dp, )

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
                ) {
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        if (localTeam != null) {
                            Image(
                                painter = painterResource(id = localTeam.clubImage!!),
                                contentDescription = "LocalTeam",
                                modifier = Modifier.size(80.dp)
                            )
                        }


                        if (localTeam != null) {
                            Text(
                                text = localTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 10.dp),
                            )
                        }
                        if (playerGoal.isEmpty()) {
                        } else {
                            for (i in 0..<playerGoal.size) {
                                for (x in 0..<playerGoal[i].goalsP) {
                                    for (z in 0..<players.size) {
                                        if (players[z].playersId == playerGoal[i].playersId) {
                                            if (localTeam != null) {
                                                if (players[z].playerTeamID == localTeam.teamsId)
                                                    Text(text = players[z].playerName)
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                    Text(
                        text = "${match.localGoals}-${match.vistGoals}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 64.dp, top = 40.dp)
                            .background(Color(0xFF5C075F), shape = RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.padding(end = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (visitorTeam != null) {
                            Image(
                                painter = painterResource(id = visitorTeam.clubImage!!),
                                contentDescription = "visitorTeam",
                                modifier = Modifier.size(80.dp)
                            )
                        }

                        if (visitorTeam != null) {
                            Text(
                                text = visitorTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,

                                )
                        }
                        if (playerGoal.isEmpty()) {
                        } else {
                            for (i in 0..<playerGoal.size) {
                                for (x in 0..<playerGoal[i].goalsP) {
                                    for (z in 0..<players.size) {
                                        if (players[z].playersId == playerGoal[i].playersId) {
                                            if (visitorTeam != null) {
                                                if (players[z].playerTeamID == visitorTeam.teamsId)
                                                    Text(text = players[z].playerName)
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
                Row {
                    Text(
                        text = "Datos del partifdo",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MatchStats(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>) {
    Card(
        modifier = Modifier
            .padding(top = 300.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Text(
                    text = makeStatsFaultsL(list, matchEntity,listP).toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Faltas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = makeStatsFaultsV(list, matchEntity,listP).toString(),
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
                    text = makeStatsYellowCardsL(list, matchEntity,listP).toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Amarillas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = makeStatsYellowCardsV(list, matchEntity,listP).toString(),
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
                    text = makeStatsRedCardsL(list, matchEntity,listP).toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Rojas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = makeStatsRedCardsV(list, matchEntity,listP).toString(),
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

fun makeStatsFaultsL(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.localTeamId){
                    cnt += list[i].foulsP
            }

        }
    }
    return cnt
}

fun makeStatsFaultsV(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.visitorTeamId){
                cnt += list[i].foulsP
            }

        }
    }
    return cnt
}
fun makeStatsYellowCardsL(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.localTeamId){
                    cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}

fun makeStatsYellowCardsV(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.visitorTeamId){
                cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}
fun makeStatsRedCardsL(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.localTeamId){
                cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}

fun makeStatsRedCardsV(list: List<MatchPlayerRelationEntity>, matchEntity: MatchEntity,listP: List<PlayerEntity>): Int {
    var cnt = 0
    for (i in list.indices) {
        for (z in listP.indices){
            if (list[i].playersId==listP[z].playersId&&listP[z].playerTeamID==matchEntity.visitorTeamId){
                cnt += list[i].yellowCardsP
            }

        }
    }
    return cnt
}

