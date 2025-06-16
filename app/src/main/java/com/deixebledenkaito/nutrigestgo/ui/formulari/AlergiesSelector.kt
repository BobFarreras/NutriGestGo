package com.deixebledenkaito.nutrigestgo.ui.formulari

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AlergiesSelector(
    selectedAlergies: List<String>,
    onAlergiesChange: (List<String>) -> Unit
) {
    val alergiesOptions = listOf("Gluten", "Ous", "Marisc", "Lactosa", "Fruits secs", "Pesca","Sèsam i mostassa", "Cap")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Tens alguna al·lèrgia o restricció? (pots seleccionar més d'una)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        alergiesOptions.forEach { alergia ->
            val checked = selectedAlergies.contains(alergia)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val newSelection = selectedAlergies.toMutableList()
                        if (alergia == "Cap") {
                            onAlergiesChange(listOf("Cap"))
                        } else {
                            if (newSelection.contains(alergia)) {
                                newSelection.remove(alergia)
                            } else {
                                newSelection.remove("Cap")
                                newSelection.add(alergia)
                            }
                            onAlergiesChange(newSelection)
                        }
                    }
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null // el click el controlem al row
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = when (alergia) {
                        "Cap" -> Icons.Filled.CheckCircle
                        "Gluten" -> Icons.Filled.BakeryDining
                        "Lactosa" -> Icons.Filled.LocalDrink
                        "Fruits secs" -> Icons.Filled.Cookie// Aquesta icona és fictícia, pots posar una altra
                        "Marisc" -> Icons.Filled.Anchor
                        "Ou" -> Icons.Filled.Egg // També fictícia, busca alternativa
                        "Pesca"-> Icons.Filled.SetMeal // També fictícia, busca alternativa
                        "sèsam i mostassa"-> Icons.Filled.PestControl// També fictícia, busca alternativa
                        else -> Icons.Default.HelpOutline
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(alergia, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}