package com.deixebledenkaito.nutrigestgo.domain.usecase.formulari

import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import javax.inject.Inject

class CheckFormCompletedUseCase @Inject constructor(
    private val userRepository: FormulariRepository
) {
    suspend operator fun invoke(userId: String): Boolean =
        userRepository.hasCompletedForm(userId)
}