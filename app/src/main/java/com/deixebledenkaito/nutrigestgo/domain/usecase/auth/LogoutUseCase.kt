package com.deixebledenkaito.nutrigestgo.domain.usecase.auth

import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authrepository: AuthRepository
) {
    suspend operator fun invoke() {
        authrepository.logout()
    }
}