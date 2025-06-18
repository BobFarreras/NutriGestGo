package com.deixebledenkaito.nutrigestgo.ui.auth


import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp



import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: (isRegister: Boolean) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.errorMessage = null
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (viewModel.isLogin) "Inicia sessió" else "Registra't",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text("Email") },
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            if (!viewModel.isLogin) {
                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = { Text("Nom de l'usuari") },
                    singleLine = true
                )
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onAuthClick(onAuthSuccess) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isLoading
            ) {
                Text(if (viewModel.isLogin) "Inicia" else "Registra't")
            }
            TextButton(onClick = { viewModel.toggleMode() }) {
                Text(
                    text = if (viewModel.isLogin) "No tens compte? Registra't" else "Ja tens compte? Inicia sessió"
                )
            }
        }
    }
}



