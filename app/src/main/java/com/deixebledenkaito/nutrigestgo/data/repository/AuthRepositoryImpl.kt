package com.deixebledenkaito.nutrigestgo.data.repository

import android.util.Log
import com.deixebledenkaito.nutrigestgo.domain.model.Usuaris
import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// data/repository/auth/AuthRepositoryImpl.kt
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    private val tag = "AuthRepositoryImpl"

    override suspend fun loginUsuari(email: String, password: String): Result<FirebaseUser> {
        return try {
            Log.d(tag, "Intentant login Usuari amb email: $email")
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw Exception("No s'ha pogut obtenir l'usuari")

            Log.d(tag, "Login exitós per l'usuari: ${user.uid}")
            Result.success(user)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e(tag, "Credencials incorrectes", e)
            Result.failure(Exception("Email o contrasenya incorrectes"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Log.e(tag, "Usuari no trobat", e)
            Result.failure(Exception("No existeix cap empresa amb aquest email"))
        } catch (e: Exception) {
            Log.e(tag, "Error desconegut en login", e)
            Result.failure(Exception("Error en l'inici de sessió: ${e.message}"))
        }
    }

    override suspend fun signupUsuari(email: String, password: String, name: String): Result<Usuaris> {
        return try {
            Log.d(tag, "Intentant registrar nou Usuari amb email: $email")

            // 1. Crear l'usuari a Firebase Auth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("No s'ha pogut crear l'usuari")

            // 2. Crear l'objecte Empresa
            val empresa = Usuaris(
                id = user.uid,
                nom = name,
                email = email
            )


            Log.d(tag, "Registre exitós per l'empresa: ${empresa.id}")
            Result.success(empresa)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Log.e(tag, "Contrasenya massa feble", e)
            Result.failure(Exception("La contrasenya és massa feble"))
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e(tag, "Email ja en ús", e)
            Result.failure(Exception("Ja existeix una empresa amb aquest email"))
        } catch (e: Exception) {
            Log.e(tag, "Error desconegut en registre", e)
            Result.failure(Exception("Error en el registre: ${e.message}"))
        }
    }

    override suspend fun logout() {
        try {
            Log.d(tag, "Tancant sessió")
            firebaseAuth.signOut()
        } catch (e: Exception) {
            Log.e(tag, "Error en logout", e)
            throw e
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}