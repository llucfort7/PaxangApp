package com.example.paxangaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity



@Database(
    entities = arrayOf(AdminLoginEntity::class), version = 2
)
abstract class AdminDB : RoomDatabase() {
    abstract fun adminLoginDAO(): AdminLoginDAO
    companion object {  // Patrón Singleton
        private var instance: AdminDB? = null

        fun getInstance(context: Context): AdminDB {
            // el método databaseBuilder devuelve una referencia a la base de datos
            return instance ?: Room.databaseBuilder(context, AdminDB::class.java, "admin-db")
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}
