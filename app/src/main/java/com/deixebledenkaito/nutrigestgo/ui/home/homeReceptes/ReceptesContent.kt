package com.deixebledenkaito.nutrigestgo.ui.home.homeReceptes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deixebledenkaito.nutrigestgo.domain.model.Recepta

@Composable

fun ReceptesContent() {
    val receptes = listOf(
        Recepta("Porridge amb fruita", listOf("avena", "llet", "plàtan"), "esmorzar"),
        Recepta("Amanida de quinoa", listOf("quinoa", "tomàquet", "formatge feta"), "dinar"),
        Recepta("Sopa de verdures", listOf("pastanaga", "api", "ceba"), "sopar"),
        // ... més receptes
    )

    val alergiesUsuari = listOf("Lactosa") // Ex. obtingudes del formulari

    val setmana = remember {
        generarSetmanaReceptes(receptes, alergiesUsuari)
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        setmana.forEach { (dia, apats) ->
            item {
                Text(dia, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                apats.forEach { recepta ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(recepta.nom, style = MaterialTheme.typography.bodyLarge)
                            Text("Ingredients: ${recepta.ingredients.joinToString(", ")}")
                        }
                    }
                }
            }
        }
    }
}
