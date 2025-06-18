package com.deixebledenkaito.nutrigestgo.ui.home.HomePerfil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseUser
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProfileContent(
    user: FirebaseUser?,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val formulari by viewModel.formulari
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(user?.email) {
        user?.email?.let { viewModel.carregarFormulari(it) }
    }

    formulari?.let {
        val items = listOf(
            Triple("⏱", "Temps per cuinar", "${it.tempsCuinar} minuts"),
            Triple("💰", "Pressupost mensual", "${it.pressupost} €"),
            Triple("⚠️", "Al·lèrgies", it.alergies.joinToString(", ").ifBlank { "Cap" }),
            Triple("🥗", "Tipus de nutrició", it.nutricio ?: "No especificat"),
            Triple("🎯", "Objectiu salut", it.objectiuSalut ?: "No especificat"),
            Triple("🚫", "Restricció alimentària", it.restriccio ?: "Cap")
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                val (icon, title, content) = items[index]
                CardInfo(icon, title, content)
            }
        }
    } ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No s’ha trobat cap formulari", color = colors.error)
        }
    }
}

@Composable
fun CardInfo(icon: String, titol: String, contingut: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(top = 20.dp)

    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titol,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = contingut,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .size(58.dp)
                .offset(y = (-38).dp)
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
