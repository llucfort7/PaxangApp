package com.example.paxangaapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class  PlayerTeamsForQueris(
    @Embedded
    val team: TeamsEntity,
    @Relation(
        parentColumn = "teamsId",
        entityColumn = "playerTeamID"
    )
    val playerEntity: List<PlayerEntity>
)