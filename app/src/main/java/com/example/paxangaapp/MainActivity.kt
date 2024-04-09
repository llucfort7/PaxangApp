package com.example.paxangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.paxangaapp.navigartion.Navigation
import com.example.paxangaapp.ui.theme.PaxangaAppTheme
import com.example.paxangaapp.ui.viwmodel.TeamsViewModel

class MainActivity : ComponentActivity() {
    private val teamsViewModel by viewModels<TeamsViewModel>()
    private val loginViwModel by viewModels<LoginViwModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaxangaAppTheme {
                Navigation(teamsViewModel,loginViwModel)
            }
        }
    }
}


