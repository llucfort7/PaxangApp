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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel
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
// Button(onClick = {
//     isLoading = true // Mostrar pantalla de carga al hacer clic en el botón
//
//     coroutineScope.launch(Dispatchers.IO) {
//         var pas = false
//         while (!pas) {
//             pas = calendario(teamsViewModel, matchViewModel, teams, navController)
//         }
//         // Una vez que se completa la operación, ocultar la pantalla de carga
//         withContext(Dispatchers.Main) {
//             isLoading = false
//         }
//     }
// }) {
//     Text("Iniciar Corrutina")
// }

// if (isLoading) {
//     LoadingScreen()
// }
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
fun calendario(
    teamsViewModel: TeamsViewModel,
    matchViewModel: MatchViewModel,
    teamList: List<TeamsEntity>,
    navController: NavHostController,
): Boolean {

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
        while (partidos.size < teamList.size / 2 ) {
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
            if (intentos>100){
                return false
            }
        }
    }
    var cntJ = 0
    var cntP = 0
    var pfinish = false
    for (n in 0..<partidosTot.size) {
        if (cntP==nEquipos/2){
            cntP=0
            cntJ++
        }
        if (partidosTot[n].matchNum != cntJ) {
            pfinish = true
        }
        cntP++
    }
    return pfinish
}

