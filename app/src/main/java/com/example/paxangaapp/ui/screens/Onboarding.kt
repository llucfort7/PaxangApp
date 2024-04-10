package com.example.paxangaapp.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.R
import com.example.paxangaapp.database.entities.AdminLoginEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondaryContainer
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.theme.md_theme_light_secondaryContainer
import com.example.paxangaapp.ui.viwmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController,teamsViewModel: TeamsViewModel,adminLoginViwModel: AdminLoginViwModel) {
    var nameState by rememberSaveable { mutableStateOf("") }
    var passw by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val myFile = "miArchivo"
    val content = "Mi primer archivo en android?"
    var file = File(context.applicationContext.filesDir, "")

    context.applicationContext.openFileOutput(myFile, Context.MODE_PRIVATE).use {
        it.write(content.toByteArray())
    }
    Column(
        modifier = Modifier
            .background(color = md_theme_light_primary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.paxangapplogo),
            contentDescription = "",
            Modifier.size(200.dp, 200.dp),
        )
        Row {
            TextField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = { Text(text = "Nombre") },
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
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextField(
                value = passw, onValueChange = { passw = it },
                label = { Text(text = "Contraseña") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "wr"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = md_theme_light_secondaryContainer, // Cambia el color del texto
                    textColor = md_theme_light_onSecondaryContainer
                )
            )
        }
        Row {
            Button(
                onClick = {
                    if (adminLoginViwModel.userExists("Admin","1111")){
                    navController.navigate(Routes.MatchScreen.routes)
                    }
                },
                enabled = nameState.length >= 3 && nameState.contains(Regex("^[A-Za-z]+\$")) && passw.length >= 3,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue, // Cambia el color de fondo del botón
                    //Preguntar per a que el boto no siga groc
                )
            ) {
                Text(text = "Siguiente")
            }
        }
       // Row {
       //     Button(onClick = {
       //         teamsViewModel.addTeam(
       //             TeamsEntity(
       //                 nameT = "Equipo1",
       //                 localicacion = "Alcasser"
       //             )
       //         )
       //     },
       //         colors = ButtonDefaults.buttonColors(
       //             containerColor = Color.Blue, // Cambia el color de fondo del botón
       //             //Preguntar per a que el boto no siga groc
       //         )) {
       //         Text(text = "Datos Demo")
       //     }
       // }
    }
}