package com.deixebledenkaito.nutrigestgo.domain.usecase.formulari

import com.deixebledenkaito.nutrigestgo.domain.model.Formulari
import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import javax.inject.Inject

class SaveFormUseCase @Inject constructor(
    private val userRepository: FormulariRepository
) {
    suspend operator fun invoke(userEmail: String, formulari: Formulari) : Result<Unit> =
        userRepository.saveForm(userEmail,formulari)
}