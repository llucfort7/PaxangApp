package com.example.paxangaapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "match")
    data class MatchEntity(
        @PrimaryKey(autoGenerate = true)
        val matchId: Int? = null,
        val localTeamId: Int=0, // Clave foránea que referencia al equipo local
        val visitorTeamId: Int=0, // Clave foránea que referencia al equipo visitante
        val date: String = "", // Aquí puedes usar el tipo de dato adecuado para la fecha en tu aplicación
        val localGoals: Int=0,
        val vistGoals: Int=0,
        val matchNum: Int=0
    )
