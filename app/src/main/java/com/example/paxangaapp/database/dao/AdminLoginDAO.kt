package com.example.paxangaapp.database.dao



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paxangaapp.database.entities.AdminLoginEntity

@Dao
interface AdminLoginDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: AdminLoginEntity)

    @Query("SELECT * FROM login")
    fun getAllUsers(): LiveData<MutableList<AdminLoginEntity>>

    @Query("SELECT Count(*) FROM login")
    fun getCountAllUsers(): Int

    @Query("DELETE FROM login")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun deleteUsers(users: AdminLoginEntity): Int
}
