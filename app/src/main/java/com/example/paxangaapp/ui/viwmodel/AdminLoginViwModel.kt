package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.AdminDB
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminLoginViwModel(application: Application) : AndroidViewModel(application) {
    private val adminLoginDAO: AdminLoginDAO = AdminDB.getInstance(application).adminLoginDAO()

    // Utilizamos MutableLiveData en lugar de LiveData
    private val _usersLiveData = MutableLiveData<List<AdminLoginEntity>>()
    val usersLiveData: LiveData<List<AdminLoginEntity>> get() = _usersLiveData

    var userList: LiveData<MutableList<AdminLoginEntity>> = MutableLiveData()


    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userList =adminLoginDAO.getAllUsers()
        }
    }

 //   fun getAllUsers(): List<AdminLoginEntity> {
 //       val response: List<AdminLoginEntity> = adminLoginDAO.getAllUsers()
 //       return response.map { it }
 //   }

    fun insertUser(user: AdminLoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            adminLoginDAO.insertUser(user)
        }
    }

    //
  //  fun anyUserExists(): Boolean {
  //      // Verificamos si userLogList no es nulo y si contiene al menos un usuario
  //      getAllUsers()
//
  //  }

     fun userExists(username: String, password: String): Boolean {
        getAllUsers()
        val users=userList.value

        // Iterar sobre la lista de usuarios y comparar cada objeto con los datos recibidos
        if (users != null) {
            for (user in users) {
                if (user.userName == username && user.password == password) {
                    return true // Si se encuentra una coincidencia, devolver true
                }
            }
        }
        // Si no se encontró ninguna coincidencia, devolver false
        return false
    }
}

