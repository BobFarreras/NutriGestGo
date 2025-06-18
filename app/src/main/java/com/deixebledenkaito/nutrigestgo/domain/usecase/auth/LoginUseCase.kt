package com.deixebledenkaito.nutrigestgo.domain.usecase.auth

import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authrepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> {
        return authrepository.loginUsuari(email, password)
    }
}