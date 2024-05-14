package com.example.paxangaapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity

@Dao
interface MatchPlayerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatchPlayerRelation(relation: MatchPlayerRelationEntity)

    @Query("SELECT * FROM match_players")
    fun getAllMatchPlayers(): LiveData<List<MatchPlayerRelationEntity>>

    @Query("SELECT * FROM match_players WHERE matchId = :matchId AND playersId = :playersId")
    fun getMatchPlayersRelation(matchId: Int, playersId: Int): MatchPlayerRelationEntity?

    @Query("SELECT * FROM match_players WHERE matchId = :matchId ")
    fun getMatchPlayersByMatch(matchId: Int): LiveData<List<MatchPlayerRelationEntity>>
    @Query("SELECT * FROM match_players WHERE matchId = :matchId AND goalsP>0")
    fun getMatchPlayersByMatchGoal(matchId: Int): LiveData<List<MatchPlayerRelationEntity>>

    @Query("DELETE FROM match_players")
    suspend fun deleteAllMatchPlayerRelations()
}