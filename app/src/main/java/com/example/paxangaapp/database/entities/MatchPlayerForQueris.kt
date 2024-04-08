package com.example.paxangaapp.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlayerWithMatch(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "matchId",
        associateBy = Junction(MatchPlayerRelationEntity::class)
    )
    val matchP: List<MatchEntity>
)

data class MatchWithPlayer(
    @Embedded val match: MatchEntity,
    @Relation(
        parentColumn = "matchId",
        entityColumn = "playerId",
        associateBy = Junction(MatchPlayerRelationEntity::class)
    )
    val playerM: List<PlayerEntity>
)