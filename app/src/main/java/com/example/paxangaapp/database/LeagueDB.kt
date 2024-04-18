package com.example.paxangaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.dao.MatchDAO
import com.example.paxangaapp.database.dao.MatchPlayerDAO
import com.example.paxangaapp.database.dao.PlayersDAO
import com.example.paxangaapp.database.dao.TeamDAO
import com.example.paxangaapp.database.dao.TeamMatchDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity
import com.example.paxangaapp.database.entities.MatchEntity
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamMatchRelationEntity
import com.example.paxangaapp.database.entities.TeamsEntity


@Database(
    entities = arrayOf(
        PlayerEntity::class,
        MatchEntity::class,
        TeamsEntity::class,
        MatchPlayerRelationEntity::class,
        TeamMatchRelationEntity::class,
        AdminLoginEntity::class
        ), version = 5
)
abstract class LeagueDB : RoomDatabase() {
    abstract fun playersDAO(): PlayersDAO
    abstract fun teamDAO(): TeamDAO
    abstract fun matchDAO(): MatchDAO
    abstract fun teamMatchDAO(): TeamMatchDAO
    abstract fun matchPlayerDAO(): MatchPlayerDAO
    abstract fun adminLoginDAO(): AdminLoginDAO


    companion object {  // Patrón Singleton
        private var instance: LeagueDB? = null

        fun getInstance(context: Context): LeagueDB {
            // el método databaseBuilder devuelve una referencia a la base de datos
            return instance ?: Room.databaseBuilder(context, LeagueDB::class.java, "league-db")
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}
