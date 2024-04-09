package com.example.paxangaapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris

@Dao
interface PlayersDAO {
    @Transaction
    @Query("SELECT * FROM players")
    fun getplayers(): List<PlayerTeamsForQueris>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE playersId = :playerId")
    suspend fun getPlayerById(playerId: Int): PlayerEntity?

    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()
}