package com.example.paxangaapp.ui.viwmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.PlayersDAO
import com.example.paxangaapp.database.entities.PlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    // Instancia del DAO
    private val playerDAO: PlayersDAO = LeagueDB.getInstance(application).playersDAO()

    // LiveData para la lista de jugadores
    var playerList: LiveData<List<PlayerEntity>> = MutableLiveData()

    var playerListByTeam: LiveData<List<PlayerEntity>> = MutableLiveData()

    var playerByid: LiveData<PlayerEntity> = MutableLiveData()


    // LiveData para el jugador seleccionado
    private val _selectedPlayer = MutableLiveData<PlayerEntity>()
    val selectedPlayer: LiveData<PlayerEntity> = _selectedPlayer

    private var _selectedPlayerById = PlayerEntity()
    val selectedPlayerById: PlayerEntity? = _selectedPlayerById

    // Método para obtener todos los jugadores
    fun getAllPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            playerList = playerDAO.getAllPlayers()
        }
    }

    // Método para agregar un nuevo jugador
    fun addPlayer(player: PlayerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDAO.insertPlayer(player)
        }
    }

    // Método para obtener un jugador por su ID
    fun getPlayerById(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerByid = playerDAO.getPlayerById(playerId)
        }
    }

    fun getPlayerByTeamId(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerListByTeam=playerDAO.getPlayerByTeamId(playerId)
        }
    }

    // Método para eliminar un jugador
 //   fun deletePlayer(player: PlayerEntity) {
 //       viewModelScope.launch(Dispatchers.IO) {
 //           playerDAO.deletePlayer(player)
 //       }
 //   }

    // Método para actualizar un jugador
    fun updatePlayer(player: PlayerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playerDAO.modPlayer(player)
        }
    }

    // Método para manejar la selección de un jugador
    fun onPlayerClicked(player: PlayerEntity) {
        _selectedPlayer.value = player
    }
}
