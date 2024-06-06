package com.example.paxangaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.paxangaapp.database.entities.AdminLoginEntity
import com.example.paxangaapp.ui.viewmodel.AdminLoginViwModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun DeleteAdmin(
    navController: NavHostController,
    adminLoginViwModel: AdminLoginViwModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista Admins") },
                actions = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) {
        adminLoginViwModel.getAllUsers()
        val users by adminLoginViwModel.userList.observeAsState(initial = emptyList())
        adminLoginViwModel.getAllUsers()

        LazyColumn(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(76.dp))
            }
            items(users) { user ->
                AdminRow(
                    user,
                    adminLoginViwModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRow(
    admin: AdminLoginEntity,
    adminLoginViwModel: AdminLoginViwModel
) {

    OutlinedCard(
         modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ListItem(
            headlineText = { Text(text = admin.userName) },
            supportingText = { Text(text = admin.adminId.toString()) },
            leadingContent = {
                IconButton(
                    onClick = {
                        if (admin.adminId!=1) {
                            adminLoginViwModel.deleteUser(admin)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Restore from trash",
                        tint = Color.Black // Puedes ajustar el color del icono si lo necesitas
                    )
                }
            }
        )
    }
}