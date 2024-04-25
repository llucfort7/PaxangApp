package com.example.paxangaapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamMatchRelationEntity
import com.example.paxangaapp.database.entities.TeamWithMach
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamMatchViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeMatches(
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchPlayerViewModel: MatchPlayerViewModel,

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
                StatsHeadline()
                MatchStats()

            }
        }
    )
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
    val num=match.matchId
    teamsViewModel.getAllTeams()
    //Le asignamos a la variable teams la lista teamlist del viewModel
    val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
    teamsViewModel.getAllTeams()

    //llamamos a la funcion del viewmodel de teams para saber asignar los equipos visitante y local ala variable
    teamsViewModel.getOneLTeam(match.localTeamId)
    teamsViewModel.getOneVTeam(match.visitorTeamId)

    //Le asignamos a la variable playeroal la lista matchPlayerListMatchGoal del viewModel
    match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatch(it) }
    val playerGoal by matchPlayerViewModel.matchPlayerListMatch.observeAsState(initial = emptyList())
    match.matchId?.let { matchPlayerViewModel.getAllMatchPlayersByMatch(it) }


    // val teamWithMatch: TeamWithMach by teamMatchViewModel.selectedTeamWithMatch.observeAsState(TeamWithMach())
    val localTeam = teams.firstOrNull { it.teamsId == match.localTeamId }
    val visitorTeam = teams.firstOrNull { it.teamsId == match.visitorTeamId }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(RoundedCornerShape(15.dp)),
    ) {
        // Image(
        //     painter = painterResource(id = R.drawable.epl_background),
        //     contentDescription = stringResource(id = R.string.epl_background),
        // )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Icon(
            //     painter = painterResource(id = R.drawable.left_arrow),
            //     contentDescription = stringResource(id = R.string.arrow_back),
            //     tint = Color.White
            // )
            Text(
                text = "String1",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            // Icon(
            //     imageVector = Icons.Default.MoreVert,
            //     contentDescription = stringResource(id = R.string.more_vert),
            //     modifier = modifier
            //         .padding(top = 10.dp),
            //     tint = Color.White
            // )
        }
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
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 10.dp),
                    fontWeight = FontWeight.SemiBold,

                    )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row() {
                            // Image(
                            //     painter = painterResource(id = R.drawable.chelsea),
                            //     contentDescription = stringResource(id = R.string.chelsea),
                            //     modifier = modifier.size(80.dp)
                            // )
                        }

                        if (localTeam != null) {
                            Text(
                                text = localTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 10.dp),
                            )
                        }

                        for (i in 0..playerGoal.size) {

                            //playerViewModel.getPlayerById(0)
                           // val play by playerViewModel.playerListByid.observeAsState(initial = emptyList())
                           // playerViewModel.getPlayerById(playeroal[i].playersId)
                            // val playerx=playerViewModel.selectedPlayerById
                            // playerViewModel.getPlayerById(playeroal[i].playersId)
                          //  if (localTeam != null) {
                          //      if (play[0].playerTeamID == localTeam.teamsId)
                                    Text(
                                        text = playerGoal[playerGoal[i].playersId].toString(),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier.padding(start = 25.dp),
                                    )
                          //  }
                        }
                    }
                    Text(
                        text = "String1",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 64.dp, top = 40.dp)
                            .background(Color(0xFF5C075F), shape = RectangleShape)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.padding(end = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Image(
                        //     painter = painterResource(id = R.drawable.manchester_united),
                        //     contentDescription = stringResource(id = R.string.manchester_united),
                        //     modifier = Modifier.size(80.dp)
                        // )
                        if (visitorTeam != null) {
                            Text(
                                text = visitorTeam.nameT,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,

                                )
                        }
                        // for (i in 0..playeroal.size){
                        //     playerViewModel.getPlayerById(playeroal[i].playersId)
                        //     val playerx: PlayerEntity by playerViewModel.selectedPlayerById.observeAsState(PlayerEntity())
                        //     playerViewModel.getPlayerById(playeroal[i].playersId)
                        //         if (visitorTeam != null) {
                        //             if (playerx.playerTeamID==visitorTeam.teamsId)
                        //                 Text(
                        //                     text = "$playerx Goal",
                        //                     style = MaterialTheme.typography.bodySmall,
                        //                     fontWeight = FontWeight.SemiBold,
                        //                     fontStyle = FontStyle.Italic,
                        //                     modifier = Modifier.padding(start = 25.dp),
                        //                 )
                        //         }
                        // }
                    }

                }
                Row {
                    Text(
                        text = match.matchNum.toString(),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StatsHeadline(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 340.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                text = "String1",
                style = MaterialTheme.typography.titleMedium
            )
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                text = "String1",
                style = MaterialTheme.typography.titleMedium
            )
        }
        FilledTonalButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                text = "String1",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun MatchStats(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(top = 400.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Column(
            modifier = modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LinearProgressIndicator(progress = 0.5f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LinearProgressIndicator(progress = 0.5f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LinearProgressIndicator(progress = 0.7f)
            }
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "String1",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinearProgressIndicator(progress = 1f)
            }
        }
    }
}

