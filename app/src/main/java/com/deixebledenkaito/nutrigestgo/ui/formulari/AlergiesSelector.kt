package com.deixebledenkaito.nutrigestgo.ui.formulari

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlergiesSelector(
    selectedAlergies: List<String>,
    onAlergiesChange: (List<String>) -> Unit
) {
    val alergiesOptions = listOf(
        "Gluten", "Ous", "Marisc", "Lactosa", "Fruits secs", "Pesca",
        "Sèsam i mostassa", "Cap"
    )

    val primaryColor = Color(0xFFEF6C00)
    val selectedBackground = Color(0xFFF5F5F5)
    val unselectedBackground = Color(0xFFF5F5F5)

    FlowRow(
        mainAxisSpacing = 12.dp,
        crossAxisSpacing = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        alergiesOptions.forEach { alergia ->
            val isSelected = selectedAlergies.contains(alergia)
            val backgroundColor = if (isSelected) selectedBackground else unselectedBackground
            val borderColor = if (isSelected) primaryColor else Color.LightGray
            val contentColor = if (isSelected) primaryColor else Color.Gray

            Surface(
                shape = RoundedCornerShape(20.dp),
                shadowElevation = if (isSelected) 6.dp else 2.dp,
                color = backgroundColor,
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier
                    .clickable {
                        val updated = selectedAlergies.toMutableList()
                        if (alergia == "Cap") {
                            onAlergiesChange(listOf("Cap"))
                        } else {
                            if (updated.contains(alergia)) {
                                updated.remove(alergia)
                            } else {
                                updated.remove("Cap")
                                updated.add(alergia)
                            }
                            onAlergiesChange(updated)
                        }
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = when (alergia) {
                            "Cap" -> Icons.Filled.CheckCircle
                            "Gluten" -> Icons.Filled.BakeryDining
                            "Lactosa" -> Icons.Filled.LocalDrink
                            "Fruits secs" -> Icons.Filled.Cookie
                            "Marisc" -> Icons.Filled.Sailing
                            "Ous" -> Icons.Filled.Egg
                            "Pesca" -> Icons.Filled.SetMeal
                            "Sèsam i mostassa" -> Icons.Filled.BugReport
                            else -> Icons.Filled.HelpOutline
                        },
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = alergia,
                        color = contentColor,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}
