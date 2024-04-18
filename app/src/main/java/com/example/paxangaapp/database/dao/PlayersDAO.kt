package com.example.paxangaapp.database.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris

@Dao
interface PlayersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE playersId = :playerId")
    fun getPlayerById(playerId: Int): LiveData<PlayerEntity?>

    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()

    @Query("DELETE FROM players WHERE playersId = :playerId")
    suspend fun deletePlayer(playerId: Int)

    @Transaction
    @Query("SELECT * FROM teams WHERE teams.teamsId = :teamId")
    fun getAllTeamsWithPlayersSameTeam(teamId: Int): LiveData<MutableList<PlayerTeamsForQueris>>
}
