package com.example.paxangaapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.navigartion.Routes
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel


@Composable
fun PlayerRow(
    player: PlayerEntity,
    playerViewModel: PlayerViewModel,
    navController: NavHostController,
) {
    OutlinedCard(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { playerViewModel.onPlayerClicked(player)
                navController.navigate(Routes.PlayerInfoScreen.routes)
            },
        //elevation = 4
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(md_theme_light_primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Columna para el nombre del jugador y más información
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${player.playersId} ${player.playerName} ${player.playerSname}",
                    //style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Aquí puedes agregar más información del jugador si lo deseas,
                // como número de jugador, posición, etc.
            }
        }
    }
}
