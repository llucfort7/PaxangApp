//package com.example.paxangaapp.database.dao
//
//import androidx.lifecycle.LiveData
//import com.example.paxangaapp.database.entities.MatchEntity
//import com.example.paxangaapp.database.entities.TeamsEntity
//import androidx.room.*
//
//interface GeneralDAO {
//    suspend fun insertMatch(match: MatchEntity)
//    fun getAllMatches(): LiveData<MutableList<MatchEntity>>
//    suspend fun getMatchById(matchId: Int): MatchEntity?
//    suspend fun deleteAllMatches():Int
//    suspend fun insertTeam(team: TeamsEntity)
//    fun getAllTeams(): LiveData<MutableList<TeamsEntity>>
//    suspend fun getTeamById(teamId: Int): TeamsEntity?
//    suspend fun deleteAllTeams()
//    suspend fun deleteTeam(team: TeamsEntity):Int
//}