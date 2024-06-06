package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_onSecondaryContainer
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.theme.md_theme_light_secondaryContainer
import com.example.paxangaapp.ui.viewmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
    adminLoginViwModel: AdminLoginViwModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) {
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
            adminLoginViwModel.getAllUsers()
            val users by adminLoginViwModel.userList.observeAsState(initial = emptyList())
            adminLoginViwModel.getAllUsers()

            Row {
                Button(
                    onClick = {
                        adminLoginViwModel.getAllUsers()
                        for (i in 0..<users.size) {
                            if (users[i].userName == nameState && users[i].password == passw) {
                                navController.navigate(Routes.MatchModifier.routes)
                            }
                        }
                    },
                    enabled = nameState.length >= 3 && nameState.contains(Regex("^[A-Za-z]+\$")) && passw.length >= 3
                ) {
                    Text(text = "Siguiente")
                }
            }
        }
    }
}