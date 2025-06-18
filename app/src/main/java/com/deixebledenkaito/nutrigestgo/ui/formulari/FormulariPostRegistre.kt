package com.deixebledenkaito.nutrigestgo.ui.formulari

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Timer

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.hilt.navigation.compose.hiltViewModel
import com.deixebledenkaito.nutrigestgo.domain.model.Formulari
import kotlinx.coroutines.launch



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FormulariPostRegistre(
    viewModel: FormulariViewModel = hiltViewModel(),
    userEmail : String,
    onFormSaved: () -> Unit

) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    // Aquí agafem l’estat del formulari des del ViewModel
    val formulari by viewModel.formulari
    val scope = rememberCoroutineScope()

    // Estadístiques formulari
    var tempsCuinar by remember { mutableStateOf(formulari.tempsCuinar.toString()) }
    var pressupost by remember {  mutableStateOf(formulari.pressupost.toString())}
    var alergies by remember { mutableStateOf(listOf<String>()) }

    var alergiesSelected by remember { mutableStateOf("Cap") }

    val nutricioOptions = listOf(
        "Omnívora" to Icons.Filled.Restaurant,
        "Vegetariana" to Icons.Filled.Eco,
        "Vegana" to Icons.Filled.Spa,
        "Crudivegana" to Icons.Filled.LocalFlorist,
        "Pescetariana" to Icons.Filled.SetMeal
    )
    var nutricioSelected by remember { mutableStateOf<String?>(formulari.nutricio) }

    val objectiusOptions = listOf(
        "Equilibrada" to Icons.Filled.Balance,
        "Terapèutica" to Icons.Filled.Healing,
        "Esportiva" to Icons.Filled.FitnessCenter
    )
    var objectiuSelected by remember { mutableStateOf<String?>(formulari.objectiuSalut) }

    val restriccionsOptions = listOf(
        "Sense gluten" to Icons.Filled.NoFood,
        "Baixa en FODMAPs" to Icons.Filled.LocalPizza,
        "Cetogènica (Keto)" to Icons.Filled.Grass,
        "Paleolítica" to Icons.Filled.Forest
    )
    var restriccioSelected by remember { mutableStateOf<String?>(formulari.restriccio) }

    var step by remember { mutableStateOf(0) }
    var error by remember { mutableStateOf<String?>(null) }

    // Actualitza el formulari amb l’entrada de l’usuari
    fun updateForm(update: Formulari.() -> Formulari) {
        viewModel.updateForm(update(formulari))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            color = colors.surface,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp)
                    .animateContentSize(animationSpec = tween(400)),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Pas ${step + 1} de 6",
                    style = MaterialTheme.typography.labelLarge.copy(color = colors.primary)
                )
                AnimatedContent<Int>(
                    targetState = step,

                ) { currentStep ->
                    when (currentStep) {
                        0 -> {
                            Column {
                                Text(
                                    "Quant temps pots dedicar a cuinar?",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = formulari.tempsCuinar.toString(),
                                    onValueChange = { input ->
                                        val filtered = input.filter { it.isDigit() }
                                        if (filtered.isNotEmpty()) {
                                            updateForm { copy(tempsCuinar = filtered.toInt()) }
                                        } else {
                                            updateForm { copy(tempsCuinar = 0) }
                                        }
                                    },
                                    label = { Text("Temps en minuts") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        1 -> {
                            Column {
                                Text(
                                    "Quin pressupost mensual tens per menjar?",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                OutlinedTextField(
                                    value = formulari.pressupost.toString(),
                                    onValueChange = { input ->
                                        val filtered = input.filter { it.isDigit() }
                                        if (filtered.isNotEmpty()) {
                                            updateForm { copy(pressupost = filtered.toDouble()) }
                                        } else {
                                            updateForm { copy(pressupost = 0.0) }
                                        }
                                    },
                                    label = { Text("Pressupost en €") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        2 -> {
                            Column {
                                Text(
                                    "Tens alguna al·lèrgia?",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                AlergiesSelector(
                                    selectedAlergies = alergies,
                                    onAlergiesChange = { alergies = it }
                                )
                            }
                        }
                        3 -> {
                            Column {
                                Text(
                                    "Selecciona tipus de nutrició",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                nutricioOptions.forEach { (label, icon) ->
                                    OpcioRadio(
                                        label = label,
                                        icon = icon,
                                        selected = nutricioSelected == label,
                                        onSelect = { nutricioSelected = label },
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )

                                }
                            }
                        }
                        4 -> {
                            Column {
                                Text(
                                    "Objectiu de salut",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                objectiusOptions.forEach { (label, icon) ->
                                    OpcioRadio(
                                        label = label,
                                        icon = icon,
                                        selected = objectiuSelected == label,
                                        onSelect = { objectiuSelected = label },
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                        5 -> {
                            Column {
                                Text(
                                    "Restriccions alimentàries",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colors.onSurface
                                )
                                Spacer(Modifier.height(12.dp))
                                restriccionsOptions.forEach { (label, icon) ->
                                    OpcioRadio(
                                        label = label,
                                        icon = icon,
                                        selected = restriccioSelected == label,
                                        onSelect = { restriccioSelected = label },
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )

                                }
                            }
                        }
                    }
                }

                if (error != null) {
                    Text(
                        error!!,
                        color = colors.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (step > 0) {
                        Button(
                            onClick = { error = null; step -= 1 },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.secondaryContainer)
                        ) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Anterior")
                            Spacer(Modifier.width(6.dp))
                            Text("Anterior")
                        }
                    } else {
                        Spacer(Modifier.width(100.dp))
                    }
                    Button(
                        onClick = {
                            error = null
                            when (step) {
                                0 -> if (tempsCuinar.isBlank() || tempsCuinar.toIntOrNull() == null) {
                                    error = "Introdueix un temps vàlid"
                                }
                                1 -> if (pressupost.isBlank() || pressupost.toDoubleOrNull() == null) {
                                    error = "Introdueix un pressupost vàlid"
                                }
                                2 -> if (alergiesSelected.isBlank()) {
                                    error = "Selecciona una opció d'al·lèrgia"
                                }
                                3 -> if (nutricioSelected == null) {
                                    error = "Selecciona un tipus de nutrició"
                                }
                                4 -> if (objectiuSelected == null) {
                                    error = "Selecciona un objectiu de salut"
                                }
                                5 -> {
                                    if (restriccioSelected == null) {
                                        error = "Selecciona una restricció alimentària"
                                    } else {
                                        // ✅ Actualitza el formulari amb totes les opcions escollides
                                        updateForm {
                                            copy(
                                                nutricio = nutricioSelected,
                                                alergies = alergies,
                                                objectiuSalut = objectiuSelected,
                                                restriccio = restriccioSelected
                                            )
                                        }
                                        Log.d("FormulariPostRegistre", "Formulari: $formulari")
                                        // ✅ Guarda al ViewModel
                                        scope.launch {
                                            val result = viewModel.saveForm(userEmail)
                                            if (result.isSuccess) {
                                                Toast.makeText(context, "Formulari desat correctament", Toast.LENGTH_SHORT).show()
                                                onFormSaved()
                                            } else {
                                                error = result.exceptionOrNull()?.localizedMessage ?: "Error desconegut"
                                            }
                                        }
                                    }
                                }
                            }
                            if (error == null && step < 5) {
                                step += 1
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text(if (step < 5) "Següent" else "Continuar", color = colors.onPrimary)
                    }
                }
            }
        }
    }
}
