package com.deixebledenkaito.nutrigestgo.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deixebledenkaito.nutrigestgo.ui.auth.AuthScreen
import com.deixebledenkaito.nutrigestgo.ui.formulari.FormulariPostRegistre
import com.deixebledenkaito.nutrigestgo.ui.formulari.FormulariViewModel
import com.deixebledenkaito.nutrigestgo.ui.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun NutriCartApp(
    viewModel: FormulariViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val hasCompletedForm by viewModel.hasCompletedForm
    val user = FirebaseAuth.getInstance().currentUser

    if (user != null && hasCompletedForm == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = when {
        user == null -> "auth"
        hasCompletedForm == false -> "formulari"
        else -> "home"
    }

    NavHost(navController, startDestination = startDestination) {
        composable("auth") {
            AuthScreen(
                onAuthSuccess = { isRegister ->
                    if (isRegister) {
                        navController.navigate("formulari") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                }
            )
        }
        composable("formulari") {
            val userEmail = user?.email ?: ""
            FormulariPostRegistre(
                viewModel = hiltViewModel(),
                userEmail= userEmail)
            {
                navController.navigate("home") {
                    popUpTo("formulari") { inclusive = true }
                }
            }
        }
        composable("home") {
            HomeScreen(navController)
        }
    }
}
