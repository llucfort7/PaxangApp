package com.example.paxangaapp.database.entities

import androidx.room.Entity

@Entity(tableName = "match_players", primaryKeys = ["matchId", "playersId"])
data class MatchPlayerRelationEntity(
    val matchId: Int,
    val playersId: Int,
    var goalsP: Int = 0,
    var foulsP: Int = 0,
    var assistsP: Int = 0,
    var yellowCardsP: Int = 0,
    var redCardsP: Int = 0,
    var timePlayed: Int = 0,
    var isPayed: Boolean = false,

)
