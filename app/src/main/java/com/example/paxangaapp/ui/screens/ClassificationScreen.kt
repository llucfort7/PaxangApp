package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClassificationScreen (
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
){
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
        teamsViewModel.getAllTeams()
        val teams by teamsViewModel.teamList.observeAsState(initial = emptyList())
        teamsViewModel.getAllTeams()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                item {
                    // Spacer para dejar espacio para la TopAppBar
                    Spacer(modifier = Modifier.height(56.dp))
                }

                items(teams) { teamsForClass ->
                    ClassificationRow(
                        teamsForClass ,navController ,teamsViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun ClassificationRow(
    teamsEntity: TeamsEntity,
    navController: NavHostController,
    teamsViewModel: TeamsViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                teamsViewModel.onTeamClicked(teamsEntity)
                navController.navigate(Routes.TeamInfoScreen.routes)
            }
    ) {
        OutlinedCard(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Número y nombre
                Row(verticalAlignment = Alignment.CenterVertically) {
                    //Text(text = teamsEntity.teamsId.toString())
                    Text(text = teamsEntity.teamsId.toString())
                    Text(text = teamsEntity.nameT)
                    // Text(text = teamsEntity.localicacion)
                }
            }
        }
    }
}
