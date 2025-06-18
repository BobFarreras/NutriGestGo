package com.deixebledenkaito.nutrigestgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.deixebledenkaito.nutrigestgo.navigation.NutriCartApp

import com.deixebledenkaito.nutrigestgo.ui.theme.NutriGestGoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriGestGoTheme {
                NutriCartApp() // Aquí cridem la teva aplicació Compose
            }
        }
    }
}