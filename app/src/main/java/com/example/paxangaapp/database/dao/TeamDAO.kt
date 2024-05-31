package com.example.paxangaapp.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris
import com.example.paxangaapp.database.entities.TeamsEntity
import com.example.paxangaapp.database.entities.PlayerEntity

@Dao
interface TeamDAO  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertTeam(team: TeamsEntity)

    @Query("SELECT * FROM teams ORDER BY points DESC")
     fun getAllTeams(): LiveData<MutableList<TeamsEntity>>
    @Query("SELECT Count(*) FROM teams")
    fun getCountTeam(): Int
    @Query("SELECT * FROM teams WHERE teamsId = :teamId")
     suspend fun getTeamById(teamId: Int): TeamsEntity?

    @Query("DELETE FROM teams")
     suspend fun deleteAllTeams()

    @Transaction
    @Query("SELECT * FROM teams")
    fun getAllTeamsWithPlayers():List<PlayerTeamsForQueris>

   // @Transaction
   // @Query("SELECT * FROM teams WHERE teams.teamsId = :teamId AND players.teamsId = :teamId")
   // fun getAllTeamsWithPlayersSameTeam(teamId: Int):List<PlayerTeamsForQueris>
  //  @Transaction
  //  @Query("SELECT * FROM PlayerTeamsForQueris WHERE team.teamsId = :teamId")
  //  fun getPlayersByTeamId(teamId: Int): List<PlayerTeamsForQueris>
}