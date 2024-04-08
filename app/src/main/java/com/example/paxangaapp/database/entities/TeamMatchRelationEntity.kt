package com.example.paxangaapp.database.entities

import androidx.room.Entity


@Entity(tableName = "teams_match", primaryKeys = ["teams", "matchId"])
data class TeamMatchRelationEntity(
    val teams: Int,
    val matchId: Int,
    val resultadoH: Int
)
