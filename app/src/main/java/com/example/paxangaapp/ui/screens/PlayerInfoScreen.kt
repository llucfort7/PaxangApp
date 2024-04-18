package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.ui.theme.md_theme_light_primary
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInfoScreen(
    navController: NavHostController,
    playersViwModel: PlayerViewModel,
) {
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


        val player: PlayerEntity by playersViwModel.selectedPlayer.observeAsState(PlayerEntity())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "${player.playerName} ${player.playerSname}",
                //style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PlayerDetailItem(
                label = "Player Number",
                value = player.playerNumber.toString()
            )
            PlayerDetailItem(
                label = "Position",
                value = player.position
            )
            PlayerDetailItem(
                label = "Good Foot",
                value = player.goodFoot
            )
            PlayerDetailItem(
                label = "Goals",
                value = player.goalsP.toString()
            )
            PlayerDetailItem(
                label = "Fouls",
                value = player.foulsP.toString()
            )
            PlayerDetailItem(
                label = "Assists",
                value = player.assistsP.toString()
            )
            PlayerDetailItem(
                label = "Yellow Cards",
                value = player.yellowCardsP.toString()
            )
            PlayerDetailItem(
                label = "Red Cards",
                value = player.redCardsP.toString()
            )
        }
    }
}
    @Composable
    fun PlayerDetailItem(
        label: String,
        value: String
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = label,
                // style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(150.dp)
            )
            Text(
                text = value,
                // style = MaterialTheme.typography.body1,
            )
        }
    }
