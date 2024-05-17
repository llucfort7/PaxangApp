package com.example.paxangaapp.ui.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val _idTeamsEdit = MutableLiveData<Int>()
    var idTeamsEdit: LiveData<Int> = _idTeamsEdit

    private val _numTeamsEdit = MutableLiveData<Int>()
    var numTeamsEdit: LiveData<Int> = _numTeamsEdit

    private val _contadorDePantallaTeam = MutableLiveData<Int>()
    var contadorDePantallaTeam: LiveData<Int> = _contadorDePantallaTeam

    private val _contadorDePantallaPlayer = MutableLiveData<Int>()
    var contadorDePantallaPlayer: LiveData<Int> = _contadorDePantallaPlayer

    private val _numPlayersEdit = MutableLiveData<Int>()
    var numPlayersEdit: LiveData<Int> = _numPlayersEdit

    fun idTeamsChangue(newId: Int) {
        _idTeamsEdit.value = newId
    }
    fun numTeamsChangue(num: Int) {
        _numTeamsEdit.value = num
    }
    fun contadorDePantallaTeamSum(sumaT: Int) {
        _contadorDePantallaTeam.value = sumaT
    }
    fun contadorDePantallaPlayerSum(sumaP: Int) {
        _contadorDePantallaPlayer.value = sumaP
    }
    fun numPlayersChangue(num: Int) {
        _numPlayersEdit.value = num
    }


}