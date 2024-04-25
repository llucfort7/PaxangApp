package com.example.paxangaapp.database.entities

import androidx.room.Entity


@Entity(tableName = "teams_match", primaryKeys = ["teamsId", "matchId"])
data class TeamMatchRelationEntity(
    val teamsId: Int,
    val matchId: Int,
    val resultadoH: Int
)
