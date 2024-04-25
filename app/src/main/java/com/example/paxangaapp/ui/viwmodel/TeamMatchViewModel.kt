package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.TeamMatchDAO
import com.example.paxangaapp.database.entities.PlayerEntity
import com.example.paxangaapp.database.entities.TeamMatchRelationEntity
import com.example.paxangaapp.database.entities.TeamWithMach
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamMatchViewModel(application: Application) : AndroidViewModel(application) {
    private val teamMatchDAO: TeamMatchDAO = LeagueDB.getInstance(application).teamMatchDAO()

    // LiveData para la lista de TeamWithMatch
    private val _teamWithMatchList = MutableLiveData<List<TeamMatchRelationEntity>>()
    val teamWithMatchList: LiveData<List<TeamMatchRelationEntity>> = _teamWithMatchList

    private val _selectedTeamWithMatch = MutableLiveData<TeamMatchRelationEntity>()
    val selectedTeamWithMatch: LiveData<TeamMatchRelationEntity> = _selectedTeamWithMatch

    // Método para obtener todos los TeamWithMatch
    fun getAllTeamWithMatch() {
        viewModelScope.launch(Dispatchers.IO) {
            val teamWithMatchListFromDB = teamMatchDAO.getAllTeamMatchRelations()
            _teamWithMatchList.postValue(teamWithMatchListFromDB)
        }
    }
    fun getAllTeamWithMatchById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val teamWithMatchListFromDB = teamMatchDAO.getTeamMatchRelation(id)
            _selectedTeamWithMatch.value=teamWithMatchListFromDB
        }
    }

  //  fun onTeamWithMachCliked(teamWithMach:TeamWithMach){
  //      _selectedTeamWithMatch.value=teamWithMach
  //  }
}