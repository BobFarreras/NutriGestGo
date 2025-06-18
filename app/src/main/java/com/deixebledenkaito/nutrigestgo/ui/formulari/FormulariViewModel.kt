package com.deixebledenkaito.nutrigestgo.ui.formulari

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deixebledenkaito.nutrigestgo.domain.usecase.formulari.CheckFormCompletedUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State

import com.deixebledenkaito.nutrigestgo.domain.model.Formulari
import com.deixebledenkaito.nutrigestgo.domain.usecase.formulari.SaveFormUseCase

@HiltViewModel
class FormulariViewModel @Inject constructor(
    private val checkFormCompletedUseCase: CheckFormCompletedUseCase,
    private val saveFormUseCase: SaveFormUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _formulari = mutableStateOf(Formulari())
    val formulari: State<Formulari> = _formulari

    private val _hasCompletedForm = mutableStateOf<Boolean?>(null)
    val hasCompletedForm: State<Boolean?> = _hasCompletedForm

    fun updateForm(newForm: Formulari) {
        _formulari.value = newForm
    }

    init {
        viewModelScope.launch {
            val user = auth.currentUser
            if (user != null) {
                _hasCompletedForm.value = checkFormCompletedUseCase(user.email ?: user.uid)
            } else {
                _hasCompletedForm.value = null
            }
        }
    }

    suspend fun saveForm(userEmail: String): Result<Unit> {
        return saveFormUseCase(userEmail, formulari.value)
    }
}
