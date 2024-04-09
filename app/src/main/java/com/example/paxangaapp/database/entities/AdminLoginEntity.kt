package com.example.paxangaapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class AdminLoginEntity(
    @PrimaryKey
    val userName: String="",
    val password: String = "",
)