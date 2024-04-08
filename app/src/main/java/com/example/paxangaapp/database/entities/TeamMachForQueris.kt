package com.example.paxangaapp.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamWithmach(
    @Embedded val team: TeamsEntity,
    @Relation(
        parentColumn = "teamsId",
        entityColumn = "matchId",
        associateBy = Junction(TeamMatchRelationEntity::class)
    )
    val matchT: List<MatchEntity>
)

data class machwithteam(
    @Embedded val match: MatchEntity,
    @Relation(
        parentColumn = "matchId",
        entityColumn = "teamsId",
        associateBy = Junction(TeamMatchRelationEntity::class)
    )
    val TeamM: List<TeamsEntity>
)