package com.example.paxangaapp.database.dao

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
    suspend fun getAllMatchPlayerRelations(): List<MatchPlayerRelationEntity>

    @Query("SELECT * FROM match_players WHERE matchId = :matchId AND playersId = :playersId")
    suspend fun getMatchPlayerRelation(matchId: Int, playersId: Int): MatchPlayerRelationEntity?

    @Query("DELETE FROM match_players")
    suspend fun deleteAllMatchPlayerRelations()
}