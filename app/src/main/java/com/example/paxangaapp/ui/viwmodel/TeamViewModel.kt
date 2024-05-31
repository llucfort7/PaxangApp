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

    private val _selectedTeamL = MutableLiveData<TeamsEntity>()
    val selectedTeamL: LiveData<TeamsEntity> = _selectedTeamL

    private val _selectedTeamV = MutableLiveData<TeamsEntity>()
    val selectedTeamV: LiveData<TeamsEntity> = _selectedTeamV

    // LiveData para el recuento de equipos

    var teamCount=0

    // Función para actualizar el recuento de equipos
    fun updateTeamCount() {
        viewModelScope.launch(Dispatchers.IO) {
            teamCount= teamDAO.getCountTeam()
        }
    }

    // Método para obtener todos los equipos
    fun getAllTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            teamList = teamDAO.getAllTeams()
        }
    }
    fun getNTeams():Int {

        var num=0
        viewModelScope.launch(Dispatchers.IO) {
            num = teamDAO.getCountTeam()
        }
        return num
    }

    // Método para agregar un nuevo equipo
    fun addTeam(team: TeamsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            teamDAO.insertTeam(team)
        }
    }
    fun getOneLTeam(teamId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val team = teamDAO.getTeamById(teamId)
            _selectedTeamL.postValue(team)
        }
    }
    fun getOneVTeam(teamId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val team = teamDAO.getTeamById(teamId)
            _selectedTeamV.postValue(team)
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
    fun deleteAllTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            teamDAO.deleteAllTeams()
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
