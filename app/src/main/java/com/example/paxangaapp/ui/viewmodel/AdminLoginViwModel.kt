package com.example.paxangaapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paxangaapp.database.AdminDB
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminLoginViwModel(application: Application) : AndroidViewModel(application) {
    private val adminLoginDAO: AdminLoginDAO = AdminDB.getInstance(application).adminLoginDAO()

    // Utilizamos MutableLiveData en lugar de LiveData
    private val _usersLiveData = MutableLiveData<List<AdminLoginEntity>>()

    var userList: LiveData<MutableList<AdminLoginEntity>> = MutableLiveData()


    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userList =adminLoginDAO.getAllUsers()
        }
    }
    fun insertUser(user: AdminLoginEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            adminLoginDAO.insertUser(user)
        }
    }
    fun deleteUser(user:AdminLoginEntity){
        viewModelScope.launch(Dispatchers.IO) {
            adminLoginDAO.deleteUser(user)
        }
    }
}

