package com.deixebledenkaito.nutrigestgo.ui.home.homeCompra

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CompraContent () {
    Column(
    modifier = Modifier
    .fillMaxSize()
    .padding(24.dp)
    ) {
        Text("Compra de la setmana", style = MaterialTheme.typography.titleLarge)
    }
}