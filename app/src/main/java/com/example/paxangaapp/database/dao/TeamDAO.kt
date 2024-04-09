package com.example.paxangaapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*
import com.example.paxangaapp.database.entities.TeamsEntity

@Dao
interface TeamDAO  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertTeam(team: TeamsEntity)

    @Query("SELECT * FROM teams")
     fun getAllTeams(): LiveData<MutableList<TeamsEntity>>

    @Query("SELECT * FROM teams WHERE teamsId = :teamId")
     suspend fun getTeamById(teamId: Int): TeamsEntity?

    @Query("DELETE FROM teams")
     suspend fun deleteAllTeams()

    @Delete
     suspend fun deleteTeam(team: TeamsEntity):Int
}