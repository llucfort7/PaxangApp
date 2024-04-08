package com.example.paxangaapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris

@Dao
interface PlayersDAO {
    @Transaction
    @Query("SELECT * FROM players")
    fun getplayers(): List<PlayerTeamsForQueris>
}