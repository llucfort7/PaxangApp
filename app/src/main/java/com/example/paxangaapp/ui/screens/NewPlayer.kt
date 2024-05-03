package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.NumberPicker
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondary
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondaryContainer
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.theme.md_theme_light_secondary
import com.example.paxangaapp.ui.theme.md_theme_light_secondaryContainer
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlayer(
    navController: NavHostController,
    playerViewModel: PlayerViewModel,
    appViewModel: AppViewModel
) {
    var playerName by rememberSaveable { mutableStateOf("") }
    var playerSName by rememberSaveable { mutableStateOf("") }
    var playerNumber by rememberSaveable { mutableStateOf(1) }
    var goodFoot = rememberSaveable { mutableStateOf("") }
    var position = rememberSaveable { mutableStateOf("") }
    val backgroundImage = painterResource(id = R.drawable.furbol)
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

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
        Column(
            modifier = Modifier
                .padding(top = 58.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = md_theme_light_primary),
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
                    // placeholder = { Text(stringResource(R.string.introduceNombre) ) },
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
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.tertiary)
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
                    .fillMaxWidth()
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
            Row(
                horizontalArrangement = Arrangement.Absolute.Center,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                val playerp = PlayerEntity(
                    playerTeamID = appViewModel.idTeamsEdit.value,
                    playerName = playerName,
                    playerSname = playerSName,
                    playerNumber = playerNumber,
                    goodFoot = goodFoot.value,
                    position = position.value,

                    )
                Button(
                    onClick = {
                        try {
                            playerViewModel.addPlayer(playerp)
                        } catch (e: Exception) {
                            Log.e("TAG", "Error al insertar jugador: ${e.message}")
                        }
                        if (appViewModel.contadorDePantallaPlayer == appViewModel.numPlayersEdit) {
                            if (appViewModel.contadorDePantallaTeam == appViewModel.numTeamsEdit) {
                                navController.navigate(Routes.TabRowMatchScreen.routes)
                            } else {
                                navController.navigate(Routes.NewTeam.routes)
                            }
                        } else {
                            navController.navigate(Routes.NewPlayer.routes)
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
