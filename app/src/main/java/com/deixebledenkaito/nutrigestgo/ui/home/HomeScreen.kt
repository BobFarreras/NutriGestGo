package com.deixebledenkaito.nutrigestgo.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon


import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.deixebledenkaito.nutrigestgo.domain.modul.BottomNavItem
import com.deixebledenkaito.nutrigestgo.ui.home.bottomBar.SettingsContent
import com.deixebledenkaito.nutrigestgo.ui.home.content.HomeContent
import com.deixebledenkaito.nutrigestgo.ui.home.topBar.ProfileContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val profilePhotoUrl = user?.photoUrl?.toString() // Pot ser null
    var selectedScreen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Benvingut/da, ${user?.email ?: "Usuari"}") },
                actions = {
                    if (profilePhotoUrl != null) {
                        AsyncImage(
                            model = profilePhotoUrl,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable {
                                    // Navega a perfil
                                    selectedScreen = "perfil"
                                },
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { selectedScreen = "perfil" }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem("Inici", Icons.Default.Home, "home"),
                    BottomNavItem("Perfil", Icons.Default.Person, "perfil"),
                    BottomNavItem("Configuració", Icons.Default.Settings, "settings")
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedScreen == item.route,
                        onClick = { selectedScreen = item.route },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                "home" -> HomeContent()
                "perfil" -> ProfileContent(user)
                "settings" -> SettingsContent(navController)
            }
        }
    }
}






