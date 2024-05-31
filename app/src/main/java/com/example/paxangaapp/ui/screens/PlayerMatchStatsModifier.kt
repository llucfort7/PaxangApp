package com.example.paxangaapp.ui.screens

import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll



@Composable
fun PlayerMatchStatsModifier(
    matchPlayerViewModel: MatchPlayerViewModel,
    matchViewModel: MatchViewModel,
    playerViewModel: PlayerViewModel,
    navController: NavController
) {
    BackHandler(enabled = true) {
        // do nothing
    }
    val match: MatchEntity by matchViewModel.selectedMatch.observeAsState(MatchEntity())
    val player: PlayerEntity by playerViewModel.selectedPlayer.observeAsState(PlayerEntity())


    val goals = rememberSaveable { mutableStateOf(0) }
    val assists = rememberSaveable { mutableStateOf(0) }
    val fouls = rememberSaveable { mutableStateOf(0) }
    val yellowCards = rememberSaveable { mutableStateOf(0) }
    val redCards = rememberSaveable { mutableStateOf(0) }
    val timePlayed = rememberSaveable { mutableStateOf(0) }

    val playerStats = match.matchId?.let {
        player.playersId?.let { playerId ->
            MatchPlayerRelationEntity(
                matchId = it,
                playersId = playerId,
                goalsP = goals.value,
                assistsP = assists.value,
                foulsP = fouls.value,
                yellowCardsP = yellowCards.value,
                redCardsP = redCards.value,
                timePlayed = timePlayed.value,
                isPayed = true
            )
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Estadísticas del Jugador",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        goals.value = modifierRow(obj = "Goles", 99)
        assists.value = modifierRow(obj = "Asistencias", 99)
        fouls.value = modifierRow(obj = "Faltas", 99)
        yellowCards.value = modifierRow(obj = "Amarillas", 2)
        redCards.value = modifierRow(obj = "Rojas", 1)
        timePlayed.value = modTimePlayed(obj = "Tiempo Jugado", 90)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                playerViewModel.updatePlayer(
                    PlayerEntity(
                        playersId = player.playersId,
                        yellowCardsP = player.yellowCardsP + yellowCards.value,
                        redCardsP = player.redCardsP + redCards.value,
                        goalsP = player.goalsP + goals.value,
                        foulsP = player.foulsP + fouls.value,
                        assistsP = player.assistsP + assists.value,
                        playerTeamID = player.playerTeamID,
                        playerName = player.playerName,
                        playerNumber = player.playerNumber,
                        playerSname = player.playerSname,
                        goodFoot = player.goodFoot,
                        position = player.position
                    )
                )
                if (playerStats != null) {
                    matchPlayerViewModel.addMatchPlayer(playerStats)
                }
                if (player.playerTeamID == match.localTeamId) {
                    val matchM = MatchEntity(
                        matchId = match.matchId,
                        localTeamId = match.localTeamId,
                        visitorTeamId = match.visitorTeamId,
                        localGoals = match.localGoals + goals.value,
                        vistGoals = match.vistGoals,
                        matchNum = match.matchNum,
                        date = match.date
                    )
                    matchViewModel.modificateMatch(matchM)
                    matchViewModel.onMatchCliked(matchM)

                } else {
                    val matchM = MatchEntity(
                        matchId = match.matchId,
                        localTeamId = match.localTeamId,
                        visitorTeamId = match.visitorTeamId,
                        vistGoals = match.vistGoals + goals.value,
                        localGoals = match.localGoals,
                        matchNum = match.matchNum,
                        date = match.date
                    )
                    matchViewModel.modificateMatch(matchM)
                    matchViewModel.onMatchCliked(matchM)
                }
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun modifierRow(obj: String, max: Int): Int {
    var currentCount by rememberSaveable { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = obj,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { currentCount-- },
                modifier = Modifier.size(36.dp),
                enabled = currentCount > 0
            ) {
                Text("-")
            }
            Text(
                text = currentCount.toString(),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(
                onClick = { currentCount++ },
                modifier = Modifier.size(36.dp),
                enabled = currentCount < max
            ) {
                Text("+")
            }
        }
    }

    return currentCount
}

@Composable
fun modTimePlayed(obj: String, max: Int): Int {
    var playerTPlayed by rememberSaveable { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = obj,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AndroidView(
                modifier = Modifier.width(50.dp),
                factory = { context ->
                    NumberPicker(context).apply {
                        setOnValueChangedListener { _, _, newval ->
                            playerTPlayed = newval

                        }
                        minValue = 1
                        maxValue = 90
                    }

                }
            )
        }
    }

    return playerTPlayed
}
