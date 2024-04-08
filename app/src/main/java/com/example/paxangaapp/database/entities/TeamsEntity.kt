package com.example.paxangaapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "teams")
data class TeamsEntity(
    @PrimaryKey(autoGenerate = true)
    val teamsId: Int? = null,
    val nameT: String = "",
    //Posar un color predeterminat
)

