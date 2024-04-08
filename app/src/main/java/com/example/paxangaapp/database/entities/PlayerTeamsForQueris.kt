package com.example.paxangaapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerTeamsForQueris(
    @Embedded
    val user: PlayerEntity,
    @Relation(
        parentColumn = "playersId",
        entityColumn = "teamsId"
    )
    val teamsEntity: List<TeamsEntity>
)