package com.example.paxangaapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paxangaapp.database.entities.MatchEntity

@Dao
interface MatchDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMatch(match: MatchEntity)

    @Query("SELECT * FROM 'match' ")
     fun getAllMatches(): LiveData<MutableList<MatchEntity>>

    @Query("SELECT * FROM 'match' WHERE matchNum = :matchId")
    fun getAllMatchesByNumMatch(matchId: Int): LiveData<MutableList<MatchEntity>>

    @Query("SELECT * FROM 'match' WHERE matchId = :matchId")
     suspend fun getMatchById(matchId: Int): MatchEntity?

    @Query("DELETE FROM 'match'")
     suspend fun deleteAllMatches(): Int
}