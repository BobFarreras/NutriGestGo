package com.deixebledenkaito.nutrigestgo.domain.usecase.auth

import com.deixebledenkaito.nutrigestgo.domain.model.Usuaris
import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import javax.inject.Inject

// domain/usecase/SignupUseCase.kt
class SignupUseCase @Inject constructor(
    private val authrepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String
    ): Result<Usuaris> {
        return authrepository.signupUsuari(email, password, name)
    }
}