package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TransferWithinAStation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondary
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondaryContainer
import com.example.paxangaapp.ui.theme.md_theme_light_secondary
import com.example.paxangaapp.ui.theme.md_theme_light_secondaryContainer
import com.example.paxangaapp.ui.viewmodel.AppViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlayer(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
    playerViewModel: PlayerViewModel,
    matchViewModel: MatchViewModel,
    appViewModel: AppViewModel
) {
    BackHandler(enabled = true) {
        // do nothing
    }
    var playerName by rememberSaveable { mutableStateOf("") }
    var playerSName by rememberSaveable { mutableStateOf("") }
    var playerNumber by rememberSaveable { mutableStateOf(1) }
    val goodFoot = rememberSaveable { mutableStateOf("") }
    val position = rememberSaveable { mutableStateOf("") }
    var alertDialog by rememberSaveable { mutableStateOf(false) }
    val backgroundImage = painterResource(id = R.drawable.furbol)
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            TopAppBar(
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

        navController.enableOnBackPressed(false)
        Column(
            modifier = Modifier
                .padding(top = 58.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            // .background(color = md_theme_light_primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Nueva Ficha",
                fontSize = 30.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle(FontStyle.FONT_SLANT_UPRIGHT)
            )
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                TextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "wr"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = md_theme_light_secondaryContainer, // Cambia el color del texto
                        textColor = md_theme_light_onSecondaryContainer
                    )

                )
            }
            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                TextField(
                    value = playerSName,
                    onValueChange = { playerSName = it },
                    placeholder = { "Nombre" },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "wr"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = md_theme_light_secondaryContainer, // Cambia el color del texto
                        textColor = md_theme_light_onSecondaryContainer
                    )
                )
            }
            Row(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(20.dp)
            ) {
                AndroidView(
                    modifier = Modifier.width(50.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            setOnValueChangedListener { _, _, newval ->
                                playerNumber = newval

                            }
                            minValue = 1
                            maxValue = 50
                        }

                    }
                )
                if (!isPortrait) {
                    Row(Modifier.height(100.dp)) {
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(70.dp)
                    .fillMaxWidth(),

                ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    Arrangement.Center
                ) {
                    Text(text = "Pie Bueno", textAlign = TextAlign.Center)
                    Icon(
                        imageVector = Icons.Default.TransferWithinAStation,
                        contentDescription = "wr"
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        horizontalArrangement = Arrangement.Center
                        //.background(MaterialTheme.colorScheme.tertiary)
                    )
                    {
                        RadioButton(
                            selected = goodFoot.value == "Derecho",
                            onClick = { goodFoot.value = "Derecho" }
                        )
                        Text("Derecho")

                        RadioButton(
                            selected = goodFoot.value == "Izquierdo",
                            onClick = { goodFoot.value = "Izquierdo" }
                        )
                        Text("izquierdo")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .paint(painter = backgroundImage, contentScale = ContentScale.Crop)
                    .width(150.dp)
                    .padding(60.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Extremo izquierdo",
                                onClick = { position.value = "Extremo izquierdo" }
                            )
                            Text("EI")
                        }
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Delantero",
                                onClick = { position.value = "Delantero" }
                            )
                            Text("DC")
                        }
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Extremo derecho",
                                onClick = { position.value = "Extremo derecho" }
                            )
                            Text("ED")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(65.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Mediocentro ofensivo",
                                onClick = { position.value = "Mediocentro ofensivo" }
                            )
                            Text("MCO")
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Mediocentro",
                                onClick = { position.value = "Mediocentro" }
                            )
                            Text("MC")
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            RadioButton(
                                selected = position.value == "Mediocentro defensivo",
                                onClick = { position.value = "Mediocentro defensivo" }
                            )
                            Text("MCD")
                        }
                    }
                }
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                RadioButton(
                                    selected = position.value == "Lateral izquierdo",
                                    onClick = { position.value = "Lateral izquierdo" }
                                )
                                Text("LI")
                            }
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                RadioButton(
                                    selected = position.value == "Defensa izquierdo",
                                    onClick = { position.value = "Defensa izquierdo" }
                                )
                                Text("DFCI")
                            }
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                RadioButton(
                                    selected = position.value == "Defensa derecho",
                                    onClick = { position.value = "Defensa derecho" }
                                )
                                Text("DFCD")
                            }
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                RadioButton(
                                    selected = position.value == "Lateral derecho",
                                    onClick = { position.value = "Lateral derecho" }
                                )
                                Text("LD")
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,

                        ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                RadioButton(
                                    selected = position.value == "Portero",
                                    onClick = { position.value = "Portero" }
                                )
                                Text("POR")
                            }
                        }

                    }


                }
            }

            if (!isPortrait) {
                Row(Modifier.height(400.dp)) {
                }
            }
            teamsViewModel.getAllTeams()
            val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
            teamsViewModel.getAllTeams()
            Row(
                horizontalArrangement = Arrangement.Absolute.Center,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                val playerp = PlayerEntity(
                    playerTeamID = appViewModel.contadorDePantallaTeam.value,
                    playerName = playerName,
                    playerSname = playerSName,
                    playerNumber = playerNumber,
                    goodFoot = goodFoot.value,
                    position = position.value,
                )
                appViewModel.contadorDePantallaTeam.value?.let { it1 ->
                    playerViewModel.getPlayerByTeamId(
                        it1
                    )
                }
                val playersT by playerViewModel.playerListByTeam.observeAsState(emptyList())

                val coroutineScope = rememberCoroutineScope()

                Button(
                    onClick = {
                        var isRepeat = false
                        for (i in 0..<playersT.size) {
                            if (playersT[i].playerNumber == playerNumber) {
                                isRepeat = true
                                alertDialog = true
                            }
                        }
                        if (isRepeat) {

                        } else {
                            try {
                                playerViewModel.addPlayer(playerp)
                            } catch (e: Exception) {
                                Log.e("TAG", "Error al insertar jugador: ${e.message}")
                            }
                            appViewModel.contadorDePantallaTeam.value?.let { it1 ->
                                appViewModel.idTeamsChangue(
                                    it1
                                )
                            }
                            appViewModel.contadorDePantallaPlayerSum(appViewModel.contadorDePantallaPlayer.value!! + 1)
                            if (appViewModel.contadorDePantallaPlayer.value == appViewModel.numPlayersEdit.value) {
                                if (appViewModel.contadorDePantallaTeam.value == appViewModel.numTeamsEdit.value) {
                                    //Clendario
                                    coroutineScope.launch(Dispatchers.Main) {
                                        var pas = false
                                        while (!pas) {
                                            pas = calendario(
                                                teamsViewModel,
                                                matchViewModel,
                                                teams,
                                                navController
                                            )
                                        }
                                    }
                                    navController.navigate(Routes.TabRowMatchScreen.routes)
                                } else {
                                    appViewModel.contadorDePantallaPlayerSum(0)
                                    navController.navigate(Routes.NewTeam.routes)
                                }
                            } else {
                                //Cambiar per new player
                                navController.navigate(Routes.NewPlayer.routes)
                            }
                        }
                    },
                    enabled = playerName.length > 3 && playerName.contains(Regex("^[A-Za-z]+\$")) && playerSName.length > 3 && playerSName.contains(
                        Regex("^[A-Za-z]+\$")
                    ) && goodFoot.value != "" && position.value != "",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = md_theme_light_secondary, // Cambia el color de fondo del botón
                        //Preguntar per a que el boto no siga groc
                        contentColor = md_theme_light_onSecondary
                    )
                ) {
                    Text(text = "Siguiente")
                }
            }
            if (alertDialog) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("El numero ya esta en uso") },
                    confirmButton = {
                        Button(
                            onClick = {
                                alertDialog = false
                            }
                        ) {
                            Text("OK")
                        }
                    })
            }
            Row(
                horizontalArrangement = Arrangement.Absolute.Center,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(10.dp)
            ) {
            }
        }
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
    val partidosDisp = mutableListOf<MatchEntity>()

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
    val nPart = partidosDisp.size
    val mitadTemporada = jornadas / 2
    var partidosTot = mutableListOf<MatchEntity>()

    var ramMatch = MatchEntity()
    for (x in 0 until jornadas) {
        var partidos = mutableListOf<MatchEntity>()
        var intentos = 0
        while (partidos.size < teamList.size / 2) {
            var cntJ = 0
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
                    cntJ++
                    ramMatch.matchNum = x
                    partidosTot.add(ramMatch)
                    partidos.add(ramMatch)
                    partidosDisp.remove(ramMatch)
                    // Registrar el partido jugado
                    listaPartidosJugados.add(ramMatch)
                    if (cntJ > teamList.size / 2) {
                        return false
                    }
                }
            }
            intentos++
            if (intentos > 100) {
                return false
            }
        }

    }
    var cntJ = 0
    var cntP = 0
    var pfinish = true
    if (partidosTot.size < nPart) {
       return false
    }
    if (partidosTot.size > nPart) {
        return false
    }

    if (partidosDisp.size != 0) {
        return false
    }
    for (z in 0..<partidosTot.size){
        matchViewModel.addMatch(partidosTot[z])
    }
    return pfinish
}

