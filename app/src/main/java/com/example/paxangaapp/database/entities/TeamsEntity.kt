package com.example.paxangaapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "teams")
data class TeamsEntity(
    @PrimaryKey(autoGenerate = true)
    val teamsId: Int? = null,
    val nameT: String = "",
    val localicacion: String = "",
    val clubImage: Int? = null,
    val points: Int=0,
    val winMatches: Int=0,
    val tieMatches: Int=0,
    val lostMatches: Int=0,
    val playedMatches: Int=0,
)

