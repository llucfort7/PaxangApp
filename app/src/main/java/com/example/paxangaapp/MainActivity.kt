package com.example.paxangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.paxangaapp.database.dao.AdminLoginDAO
import com.example.paxangaapp.navigartion.Navigation
import com.example.paxangaapp.ui.theme.PaxangaAppTheme
import com.example.paxangaapp.ui.viwmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viwmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viwmodel.MatchViewModel
import com.example.paxangaapp.ui.viwmodel.PlayerViewModel
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

class MainActivity : ComponentActivity() {
    private val matchViewModel by viewModels<MatchViewModel>()
    private val teamsViewModel by viewModels<TeamsViewModel>()
    private val adminLoginViwModel by viewModels<AdminLoginViwModel>()
    private val playerViewModel by viewModels<PlayerViewModel>()
    private val matchPlayerViewModel by viewModels<MatchPlayerViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaxangaAppTheme {
                Navigation(matchViewModel, teamsViewModel, adminLoginViwModel,playerViewModel,matchPlayerViewModel)
            }
        }
    }
}


