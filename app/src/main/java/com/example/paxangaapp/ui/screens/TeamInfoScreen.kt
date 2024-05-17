package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
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
        val team: TeamsEntity by teamsViewModel.selectedTeam.observeAsState(TeamsEntity())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
        val players by playersViwModel.playerListByTeam.observeAsState(emptyList())
        team.teamsId?.let { it1 -> playersViwModel.getPlayerByTeamId(it1) }
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
                            .padding(5.dp),
                        contentScale = ContentScale.Fit // Cambia la escala de contenido a Fit
                    )
                }
            }
            Text(
                text = team.nameT,
                //style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            teamsViewModel.getAllTeams()
            val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
            teamsViewModel.getAllTeams()
            var isLoading by rememberSaveable { mutableStateOf(false) } // Estado para controlar si se está cargando
            val corutineScope = rememberCoroutineScope()

            Button(onClick = {

                isLoading = true // Mostrar pantalla de carga al hacer clic en el botón

                corutineScope.launch(Dispatchers.IO){
                    var pas =false
                    while (!pas){
                        pas=calendario(teamsViewModel, matchViewModel, teams, navController)
                    }
                }
                    isLoading = false


            }) {
                Text("Iniciar Corrutina")
            }
            if (isLoading) {
                LoadingScreen()
            }

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
                items(players) { player ->
                    PlayerRow(
                        player, playersViwModel, navController
                    )
                }
            }
        }
    }
}
suspend fun calendario(
    teamsViewModel: TeamsViewModel,
    matchViewModel: MatchViewModel,
    teamList: List<TeamsEntity>,
    navController: NavHostController,
):Boolean {

    matchViewModel.deleteAllMatches()
    val nEquipos = teamList.size
    val jornadas = (nEquipos - 1) * 2
    // Crear una copia mutable de la lista de equipos disponibles
    var partidosDisp = mutableListOf<MatchEntity>()

    // Lista para rastrear los partidos jugados
    val listaPartidosJugados = mutableListOf<MatchEntity>()

    for (i in 0 until teamList.size) {
        for (z in 0 until teamList.size) {
            val localT = teamList[i]
            val visitTeam = teamList[z]
            if (localT != visitTeam) {
                localT.teamsId?.let {
                    visitTeam.teamsId?.let { it1 ->
                        MatchEntity(
                            localTeamId = it,
                            visitorTeamId = it1
                        )
                    }
                }?.let {
                    partidosDisp.add(
                        it
                    )
                }
            }
        }
    }

    val mitadTemporada = jornadas / 2
    var partidosTot = mutableListOf<MatchEntity>()

    var ramMatch = MatchEntity()
    for (x in 0 until jornadas) {
        var partidos = mutableListOf<MatchEntity>()
        var intentos = 0
        while (partidos.size < teamList.size / 2 && intentos < 100) {
            ramMatch = partidosDisp.random()
            // Verificar si los equipos ya jugaron antes de la mitad de la temporada
            if (listaPartidosJugados.count {
                    (it.localTeamId == ramMatch.localTeamId && it.visitorTeamId == ramMatch.visitorTeamId) ||
                            (it.localTeamId == ramMatch.visitorTeamId && it.visitorTeamId == ramMatch.localTeamId)
                } < mitadTemporada) {
                if (!partidos.any {
                        it.localTeamId == ramMatch.localTeamId ||
                                it.localTeamId == ramMatch.visitorTeamId ||
                                it.visitorTeamId == ramMatch.localTeamId ||
                                it.visitorTeamId == ramMatch.visitorTeamId
                    }) {
                    ramMatch.matchNum = x
                    partidosTot.add(ramMatch)
                    matchViewModel.addMatch(ramMatch)
                    partidos.add(ramMatch)
                    partidosDisp.remove(ramMatch)
                    // Registrar el partido jugado
                    listaPartidosJugados.add(ramMatch)
                }
            }
            intentos++
        }
    }
    return if (partidosDisp.size==0){
        true
    }else{
        false
    }
}

