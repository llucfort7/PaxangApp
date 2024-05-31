package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.paxangaapp.ui.common.TeamImages
import com.example.paxangaapp.ui.theme.md_theme_dark_inverseOnSurface
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondary
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondaryContainer
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.theme.md_theme_light_secondary
import com.example.paxangaapp.ui.theme.md_theme_light_secondaryContainer
import com.example.paxangaapp.ui.viwmodel.AppViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel
import okhttp3.Route
import kotlin.random.Random

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun NewTeam(
    teamsViewModel: TeamsViewModel,
    navController: NavHostController,
    appViewModel: AppViewModel
) {

    var teamName by rememberSaveable { mutableStateOf("") }
    var teamLocation by rememberSaveable { mutableStateOf("") }
    var playersNumber by rememberSaveable { mutableStateOf(3) }
    //val backgroundImage = painterResource(id = R.drawable.furbol)
    val configuration = LocalConfiguration.current
    var selectedImage by rememberSaveable { mutableStateOf<Int?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = md_theme_light_primary
                ),
                title = { Text(text = "PAXANGAPP") },
                actions = {


                },
            )
        }

    ) {
        BackHandler(enabled = true) {
            // do nothing
        }
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
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text(text = "Nombre del club") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "wr"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = md_theme_light_secondaryContainer, // Cambia el color del texto
                        textColor = Color.Black
                    )

                )
            }
            Row(

            ) {
                TextField(
                    value = teamLocation,
                    onValueChange = { teamLocation = it },
                    label = { Text(text = "Barrio/Lugar") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "wr"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = md_theme_light_secondaryContainer, // Cambia el color del texto
                        textColor = Color.Black
                    )

                )
            }
            AndroidView(
                modifier = Modifier.width(50.dp),
                factory = { context ->
                    NumberPicker(context).apply {
                        setOnValueChangedListener { _, _, newval ->
                            playersNumber = newval

                        }
                        minValue = 2
                        maxValue = 15
                    }

                }
            )

            // Muestra las imágenes de los equipos
            val imagenesClub = TeamImages.obtenerImagenes()
            val filasDeImagenes = imagenesClub.chunked(4)
            for (fila in filasDeImagenes) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (imagenId in fila) {
                        val isSelected = imagenId == selectedImage
                        val borderWidth = if (isSelected) 5.dp else 0.dp
                        val borderColor = if (isSelected) Color.Blue else Color.Transparent

                        Surface(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(4.dp)
                                .clickable {
                                    // Al hacer clic en una imagen, actualiza la imagen seleccionada en el ViewModel
                                    selectedImage = if (isSelected) null else imagenId
                                },
                            shape = CircleShape,
                            border = BorderStroke(borderWidth, borderColor)
                        ) {
                            Image(
                                painter = painterResource(imagenId),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            teamsViewModel.getAllTeams()
            val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
            // Botón para agregar el equipo
            Button(
                onClick = {
                    if (selectedImage != null) {
                        teamsViewModel.addTeam(
                            TeamsEntity(
                                teamsId = appViewModel.idTeamsEdit.value?.plus(1),
                                nameT = teamName,
                                localicacion = teamLocation,
                                clubImage = selectedImage
                            )
                        )
                    }
                    selectedImage?.let { it1 -> TeamImages.anyadirImagenRet(it1) }

                    appViewModel.numPlayersChangue(playersNumber)
                    //appViewModel.idTeamsEdit.value?.let { it1 -> appViewModel.idTeamsChangue(it1) }
                    appViewModel.contadorDePantallaTeamSum(appViewModel.contadorDePantallaTeam.value!! + 1)
                    navController.navigate(Routes.NewPlayer.routes)

                },
                enabled = selectedImage != null && teamName.length > 3 && teamName.contains(Regex("^[A-Za-z]+\$")) && teamLocation.length > 3 && teamLocation.contains(
                    Regex("^[A-Za-z]+\$")
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_secondary,
                    contentColor = md_theme_light_onSecondary
                )
            ) {
                Text(text = "Siguiente")
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

