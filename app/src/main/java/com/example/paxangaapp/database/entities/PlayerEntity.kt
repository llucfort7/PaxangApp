package com.example.paxangaapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val playersId: Int? = null,
    val playerNumber: Int = 0,
    val playerName: String = "",
    val playerSname: String = "",
    val goodFoot: String = "",
    val position: String = "",

    //var favoriteP: Boolean = false,
   // var goalsP: Int = 0,
   // var foulsP: Int = 0,
   // var assistsP: Int = 0,
   // var yellowCardsP: Int = 0,
   // var redCardsP: Int = 0,
)
