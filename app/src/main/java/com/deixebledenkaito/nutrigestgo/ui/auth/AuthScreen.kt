package com.deixebledenkaito.nutrigestgo.ui.auth


import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.unit.dp



import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.deixebledenkaito.nutrigestgo.ui.components.BackgroundApp


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

    val backgroundColor = if (viewModel.isLogin) BackgroundApp().backgroundColor else Color(0xFFE1F5FE)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Títol simpàtic amb emoji
            Text(
                text = if (viewModel.isLogin) "🍏 Inicia sessió" else "🥑 Registra't",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4E342E)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            AuthTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = "Email",
                icon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            AuthTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = "Contrasenya",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            if (!viewModel.isLogin) {
                Spacer(modifier = Modifier.height(12.dp))
                AuthTextField(
                    value = viewModel.name,
                    onValueChange = { viewModel.name = it },
                    label = "Nom d'usuari",
                    icon = Icons.Default.Person
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onAuthClick(onAuthSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.isLogin) Color(0xFFEF6C00) else Color(0xFF039BE5),
                    contentColor = Color.White
                ),
                enabled = !viewModel.isLoading
            ) {
                Text(
                    text = if (viewModel.isLogin) "🍽️ Inicia" else "🎉 Registra't",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { viewModel.toggleMode() }) {
                Text(
                    text = if (viewModel.isLogin) "No tens compte? Registra't 🎈" else "Ja tens compte? Inicia sessió 🔐",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                )
            }
        }
    }
}
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF4E342E))  },
        leadingIcon = { Icon(icon, contentDescription = null) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF8D6E63),
            cursorColor = Color(0xFF6D4C41)
        )
    )
}




