package com.example.paxangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.paxangaapp.navigartion.Navigation
import com.example.paxangaapp.ui.theme.PaxangaAppTheme
import com.example.paxangaapp.ui.viewmodel.AdminLoginViwModel
import com.example.paxangaapp.ui.viewmodel.AppViewModel
import com.example.paxangaapp.ui.viewmodel.MatchPlayerViewModel
import com.example.paxangaapp.ui.viewmodel.MatchViewModel
import com.example.paxangaapp.ui.viewmodel.PlayerViewModel
import com.example.paxangaapp.ui.viewmodel.TeamsViewModel

class MainActivity : ComponentActivity() {
    private val matchViewModel by viewModels<MatchViewModel>()
    private val teamsViewModel by viewModels<TeamsViewModel>()
    private val adminLoginViwModel by viewModels<AdminLoginViwModel>()
    private val playerViewModel by viewModels<PlayerViewModel>()
    private val matchPlayerViewModel by viewModels<MatchPlayerViewModel>()
private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaxangaAppTheme {
                Navigation(matchViewModel, teamsViewModel, adminLoginViwModel,playerViewModel,matchPlayerViewModel,appViewModel)
            }
        }
    }
}


