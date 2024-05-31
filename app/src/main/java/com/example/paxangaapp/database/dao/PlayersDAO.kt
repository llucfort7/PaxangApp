package com.example.paxangaapp.database.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris

@Dao
interface PlayersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)
    @Upsert
    suspend fun modPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): LiveData<List<PlayerEntity>>
    @Query("SELECT * FROM players ORDER BY goalsP DESC")
    fun getAllPlayersByGoals(): LiveData<List<PlayerEntity>>
    @Query("SELECT * FROM players ORDER BY assistsP DESC")
    fun getAllPlayersByAsists(): LiveData<List<PlayerEntity>>
    @Query("SELECT * FROM players ORDER BY yellowCardsP DESC")
    fun getAllPlayersByYellowC(): LiveData<List<PlayerEntity>>
    @Query("SELECT * FROM players ORDER BY redCardsP DESC")
    fun getAllPlayersByRedC(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE playersId = :playerId")
    fun getPlayerById(playerId: Int): LiveData<PlayerEntity>

    @Query("SELECT * FROM players WHERE playerTeamID = :playerTId")
    fun getPlayerByTeamId(playerTId: Int): LiveData<List<PlayerEntity>>



    @Query("DELETE FROM players ")
    suspend fun deleteAllPlayers()

    @Transaction
    @Query("SELECT * FROM teams WHERE teams.teamsId = :teamId")
    fun getAllTeamsWithPlayersSameTeam(teamId: Int): LiveData<MutableList<PlayerTeamsForQueris>>
}
