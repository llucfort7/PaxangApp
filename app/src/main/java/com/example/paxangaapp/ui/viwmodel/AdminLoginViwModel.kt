package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paxangaapp.database.LeagueDB
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.database.entities.AdminLoginEntity

class AdminLoginViwModel(application: Application) : AndroidViewModel(application) {
    // Instancia del DAO
    private val adminLoginDAO: AdminLoginDAO = LeagueDB.getInstance(application).adminLoginDAO()

    // LiveData para la lista de equipos
    var UserLogList: LiveData<MutableList<AdminLoginEntity>> = MutableLiveData()


}