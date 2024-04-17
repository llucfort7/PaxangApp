package com.example.paxangaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

@Composable
fun MatchRow(
    navController: NavHostController,
    matchViewModel: MatchViewModel,
    matchEntity: MatchEntity,
    teamsViewModel: TeamsViewModel
) {
    val selectedTeamL by teamsViewModel.selectedTeamL.observeAsState()
    val selectedTeamV by teamsViewModel.selectedTeamV.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                // Icono y nombre del equipo 1
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Equipo 1",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    teamsViewModel.getOneLTeam(matchEntity.localTeamId)
                    selectedTeamL?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }

                // Resultado
                Text(
                    text = "${matchEntity.localGoals.toString()}-${matchEntity.vistGoals.toString()}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Icono y nombre del equipo 2
                Row(verticalAlignment = Alignment.CenterVertically) {
                    teamsViewModel.getOneVTeam(matchEntity.visitorTeamId)
                    selectedTeamV?.let {
                        Text(
                            text = it.nameT,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Equipo2",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
