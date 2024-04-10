package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class AdminLoginViwModel(application: Application) : AndroidViewModel(application) {
    private val adminLoginDAO: AdminLoginDAO = LeagueDB.getInstance(application).adminLoginDAO()

    // Utilizamos MutableLiveData en lugar de LiveData
    private val _userLogList: MutableLiveData<MutableList<AdminLoginEntity>> = MutableLiveData()
    val userLogList: LiveData<MutableList<AdminLoginEntity>> get() = _userLogList

    init {
        // Cargar usuarios al inicializar el ViewModel
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = adminLoginDAO.getAllUsers().value
            users?.let {
                Log.d("AdminLoginViewModel", "Users: $it")
                _userLogList.postValue(it)
            }
        }
    }

    fun insertUser(user: AdminLoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            adminLoginDAO.insertUser(user)
            // Después de insertar, volvemos a obtener todos los usuarios
            getAllUsers()
        }
    }

    fun anyUserExists(): Boolean {
        // Verificamos si userLogList no es nulo y si contiene al menos un usuario
        return userLogList.value?.isNotEmpty() ?: false
    }

    fun userExists(entryUsername: String, entryPassword: String): Boolean {
        getAllUsers()
        // Verificamos si userLogList no es nulo y si contiene al menos un usuario con el nombre de usuario y contraseña dados
        return userLogList.value?.any { it.userName == entryUsername && it.password == entryPassword } ?: false
    }
}

