package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.entities.PlayerTeamsForQueris
import com.example.paxangaapp.database.entities.TeamsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerTeamsViewModel(application: Application) : AndroidViewModel(application) {

    private val playerTeamsForQuerisDAO = LeagueDB.getInstance(application).playersDAO()


    // private val _teamList = MutableLiveData<List<PlayerTeamsForQueris>>()
    var teamList: LiveData<MutableList<PlayerTeamsForQueris>> = MutableLiveData()
    fun getAllTeamsWithPlayersSameTeam(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            teamList = playerTeamsForQuerisDAO.getAllTeamsWithPlayersSameTeam(id)

        }
    }
}

