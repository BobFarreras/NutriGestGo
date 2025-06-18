package com.deixebledenkaito.nutrigestgo.domain.repository

import com.deixebledenkaito.nutrigestgo.domain.model.Formulari

interface FormulariRepository {
    suspend fun saveForm(userEmail: String, formulari: Formulari): Result<Unit>
    suspend fun hasCompletedForm(userEmail: String): Boolean
    suspend fun getFormulari(userEmail: String): Formulari?
}