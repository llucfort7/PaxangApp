package com.example.paxangaapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paxangaapp.database.entities.TeamMatchRelationEntity

@Dao
interface TeamMatchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMatchRelation(relation: TeamMatchRelationEntity)

    @Query("SELECT * FROM teams_match")
    suspend fun getAllTeamMatchRelations(): List<TeamMatchRelationEntity>

    @Query("SELECT * FROM teams_match WHERE teams = :teamId AND matchId = :matchId")
    suspend fun getTeamMatchRelation(teamId: Int, matchId: Int): TeamMatchRelationEntity?

    @Query("DELETE FROM teams_match")
    suspend fun deleteAllTeamMatchRelations()
}