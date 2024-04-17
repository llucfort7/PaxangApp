package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.MatchDAO
import com.example.paxangaapp.database.entities.MatchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchViewModel(application: Application) : AndroidViewModel(application) {
    // Instancia del DAO
    private val matchDAO: MatchDAO = LeagueDB.getInstance(application).matchDAO()

    // LiveData para la lista de partidos
    var matchList: LiveData<MutableList<MatchEntity>> = MutableLiveData()

    // Método para obtener todos los partidos
    fun getAllMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchList = matchDAO.getAllMatches()
        }
    }

    // Método para agregar un nuevo partido
    fun addMatch(match: MatchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            matchDAO.insertMatch(match)
        }
    }

    // Método para obtener un partido por su ID
    suspend fun getMatchById(matchId: Int): MatchEntity? {
        return matchDAO.getMatchById(matchId)
    }

    // Método para eliminar todos los partidos
    fun deleteAllMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchDAO.deleteAllMatches()
        }
    }
}
