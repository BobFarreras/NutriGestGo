package com.deixebledenkaito.nutrigestgo.domain.repository

import com.deixebledenkaito.nutrigestgo.domain.model.Usuaris
import com.google.firebase.auth.FirebaseUser

//domain/repository/AuthRepository
interface AuthRepository {
    suspend fun loginUsuari(email: String, password: String): Result<FirebaseUser>
    suspend fun signupUsuari(email: String, password: String, name: String): Result<Usuaris>
    suspend fun logout()
    fun getCurrentUser(): FirebaseUser?

}