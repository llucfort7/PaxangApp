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

    var matchListNumber: LiveData<MutableList<MatchEntity>> = MutableLiveData()

    private val _selectedMatch = MutableLiveData<MatchEntity>()
    val selectedMatch: LiveData<MatchEntity> = _selectedMatch


    private val _nplayedMatches = MutableLiveData<Int>()
    val nplayedMatches: LiveData<Int> = _nplayedMatches

    private val _selectedMatchId = MutableLiveData<MatchEntity>()
    val selectedMatchId: LiveData<MatchEntity> = _selectedMatchId
    // Método para obtener todos los partidos
    fun getAllMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchList = matchDAO.getAllMatches()
        }
    }
    fun getAllMatchesByNumMatch(nMatch:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            matchListNumber = matchDAO.getAllMatchesByNumMatch(nMatch)
        }
    }

    // Método para agregar un nuevo partido
    fun addMatch(match: MatchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            matchDAO.insertMatch(match)
        }
    }
    fun modificateMatch(match: MatchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            matchDAO.modifierMatch(match)
        }
    }

    // Método para obtener un partido por su ID
     fun getMatchById(matchId: Int){
        viewModelScope.launch(Dispatchers.IO) {
        _selectedMatchId.value= matchDAO.getMatchById(matchId)
        }
    }

    // Método para eliminar todos los partidos
    fun deleteAllMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchDAO.deleteAllMatches()
        }
    }
    fun getNMtachesPlayed():Int {
        var ret=0
        viewModelScope.launch(Dispatchers.IO) {
           ret= matchDAO.getNMAtchesPlayed()
        }
        return ret
    }
    fun onMatchCliked(match:MatchEntity){
        _selectedMatch.value=match
    }


}
