package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.MatchPlayerDAO
import com.example.paxangaapp.database.entities.MatchPlayerRelationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchPlayerViewModel(application: Application) : AndroidViewModel(application) {
    // Instancia del DAO
    private val matchPlayersDAO: MatchPlayerDAO = LeagueDB.getInstance(application).matchPlayerDAO()

    // LiveData para la lista de relaciones entre partidos y jugadores
    var matchPlayerList: LiveData<List<MatchPlayerRelationEntity>> = MutableLiveData()

    var matchPlayerListMatch: LiveData<List<MatchPlayerRelationEntity>> = MutableLiveData()

    var matchPlayerListMatchGoal: LiveData<List<MatchPlayerRelationEntity>> =MutableLiveData()
    // Método para agregar una nueva relación entre partido y jugador
    fun addMatchPlayer(matchPlayer: MatchPlayerRelationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            matchPlayersDAO.insertMatchPlayerRelation(matchPlayer)
        }
    }

    // Método para obtener todas las relaciones entre partidos y jugadores
    fun getAllMatchPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            matchPlayerList = matchPlayersDAO.getAllMatchPlayers()
        }
    }
    fun getAllMatchPlayersByMatch(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            matchPlayerListMatch = matchPlayersDAO.getMatchPlayersByMatch(id)
        }
    }
    fun getAllMatchPlayersByMatchGoal(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            matchPlayerListMatchGoal = matchPlayersDAO.getMatchPlayersByMatchGoal(id)
        }
    }
    // Método para actualizar una relación entre partido y jugador
    //fun updateMatchPlayer(matchPlayer: MatchPlayerRelationEntity) {
    //    viewModelScope.launch(Dispatchers.IO) {
    //        matchPlayersDAO.updateMatchPlayer(matchPlayer)
    //    }
    //}
//
    //// Método para eliminar una relación entre partido y jugador
    //fun deleteMatchPlayer(matchPlayer: MatchPlayerRelationEntity) {
    //    viewModelScope.launch(Dispatchers.IO) {
    //        matchPlayersDAO.deleteMatchPlayer(matchPlayer)
    //    }
    //}
}
