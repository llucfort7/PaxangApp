package com.example.paxangaapp.ui.viwmodel



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.TeamDAO
import com.example.paxangaapp.database.entities.TeamsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamsViewModel(application: Application) : AndroidViewModel(application) {
    // Instancia del DAO
    private val teamDAO: TeamDAO = LeagueDB.getInstance(application).teamDAO()

    // LiveData para la lista de equipos
    var teamList: LiveData<MutableList<TeamsEntity>> = MutableLiveData()

    // LiveData para el equipo seleccionado
    private val _selectedTeam = MutableLiveData<TeamsEntity>()
    val selectedTeam: LiveData<TeamsEntity> = _selectedTeam

    // LiveData para el recuento de equipos
    private val _teamCount = MutableLiveData<Int>()
    val teamCount: LiveData<Int> = _teamCount

    // Función para actualizar el recuento de equipos
    private fun updateTeamCount(count: Int) {
        _teamCount.value = count
    }

    // Método para obtener todos los equipos
    fun getAllTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            teamList = teamDAO.getAllTeams()
        }
    }

    // Método para agregar un nuevo equipo
    fun addTeam(team: TeamsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            teamDAO.insertTeam(team)
        }
    }

    // Método para verificar si existen equipos
   // fun checkTeamExistence() {
   //     viewModelScope.launch(Dispatchers.IO) {
   //         val count = teamDAO.getAllTeams().size
   //         withContext(Dispatchers.Main) {
   //             updateTeamCount(count)
   //         }
   //     }
   // }

    // Método para eliminar un equipo
    fun deleteTeam(team: TeamsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            teamDAO.deleteTeam(team)
        }
    }

    // Método para actualizar un equipo
    fun updateTeam(team: TeamsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            teamDAO.insertTeam(team)
        }
    }

    // Método para manejar la selección de un equipo
    fun onTeamClicked(team: TeamsEntity) {
        _selectedTeam.value = team
    }
}
