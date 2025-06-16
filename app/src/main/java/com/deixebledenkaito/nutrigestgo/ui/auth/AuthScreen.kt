package com.deixebledenkaito.nutrigestgo.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import com.deixebledenkaito.nutrigestgo.data.repository.AuthRepository.registerWithEmail
import com.deixebledenkaito.nutrigestgo.data.repository.AuthRepository.signInWithEmail
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(error) {
        val user = FirebaseAuth.getInstance().currentUser
        Log.d("Auth", "Usuari actual: $user")
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            error = null
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isLogin) "Inicia Sessió" else "Registra't",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        error = "Si us plau, introdueix email i contrasenya vàlids"
                        return@Button
                    }

                    if (isLogin) {
                        signInWithEmail(email, password, {
                            onLoginSuccess()
                        }, onError = {
                            error = parseFirebaseError(it)
                        })
                    } else {
                        registerWithEmail(email, password, {
                            onRegisterSuccess()
                        }, onError = {
                            error = parseFirebaseError(it)
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = if (isLogin) "Inicia sessió" else "Registra't")
            }

            TextButton(onClick = { isLogin = !isLogin }) {
                Text(
                    text = if (isLogin) "No tens compte? Registra't" else "Ja tens compte? Inicia sessió",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Aquests són helpers que cal que implementis (o tens ja) a nivell Firebase
fun signInWithEmail(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception ->
            onError(exception.localizedMessage ?: "Error desconegut")
        }
}

fun registerWithEmail(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception ->
            onError(exception.localizedMessage ?: "Error desconegut")
        }
}

fun parseFirebaseError(rawMessage: String): String {
    val msg = rawMessage.lowercase()
    return when {
        "email address is already in use" in msg ->
            "Aquest email ja està en ús per un altre compte."
        "email address is badly formatted" in msg ->
            "El format de l'email no és correcte."
        "password is invalid" in msg || "wrong password" in msg ->
            "Contrasenya incorrecta."
        "no user record" in msg || "there is no user record" in msg ->
            "Aquest usuari no existeix."
        "password should be at least" in msg ->
            "La contrasenya és massa curta."
        "network error" in msg ->
            "Error de connexió. Comprova la teva connexió a Internet."
        "user disabled" in msg ->
            "Aquest usuari està deshabilitat."
        else -> rawMessage
    }
}

