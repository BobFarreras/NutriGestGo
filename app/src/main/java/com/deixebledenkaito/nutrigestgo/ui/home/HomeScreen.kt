package com.deixebledenkaito.nutrigestgo.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem


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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.deixebledenkaito.nutrigestgo.domain.model.BottomNavItem
import com.deixebledenkaito.nutrigestgo.ui.home.HomeSettings.SettingsContent
import com.deixebledenkaito.nutrigestgo.ui.home.homePrincipal.HomeContent
import com.deixebledenkaito.nutrigestgo.ui.home.HomePerfil.ProfileContent
import com.deixebledenkaito.nutrigestgo.ui.home.homeCalendari.CalendariContent
import com.deixebledenkaito.nutrigestgo.ui.home.homeCompra.CompraContent
import com.deixebledenkaito.nutrigestgo.ui.home.homeEstadistica.EstadisticaContent
import com.deixebledenkaito.nutrigestgo.ui.home.homeReceptes.ReceptesContent
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val profilePhotoUrl = user?.photoUrl?.toString() // Pot ser null
    var selectedScreen by remember { mutableStateOf("home") }
    var expandedMenu by remember { mutableStateOf(false) }  // Estat per desplegable

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Benvingut/da") },
                actions = {
                    Box {
                        if (profilePhotoUrl != null) {
                            AsyncImage(
                                model = profilePhotoUrl,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .clickable { expandedMenu = true },
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Perfil",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { expandedMenu = true }
                            )
                        }

                        DropdownMenu(
                            expanded = expandedMenu,
                            onDismissRequest = { expandedMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Perfil") },
                                onClick = {
                                    selectedScreen = "perfil"
                                    expandedMenu = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Configuració") },
                                onClick = {
                                    selectedScreen = "settings"
                                    expandedMenu = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Settings, contentDescription = null)
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem("Inici", Icons.Default.Home, "home"),
                    BottomNavItem("Receptes", Icons.Default.RestaurantMenu, "receptes"),
                    BottomNavItem("Calendari", Icons.Default.DateRange, "calendari"),
                    BottomNavItem("Estadística", Icons.Default.BarChart, "estadistica") ,
                    BottomNavItem("Compra", Icons.Default.ShoppingCart, "compra")
                    // Perfil i Settings fora de la BottomBar
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
                "receptes" -> ReceptesContent()
                "calendari" -> CalendariContent()
                "estadistica" -> EstadisticaContent()
                "compra" -> CompraContent()
            }
        }
    }
}



