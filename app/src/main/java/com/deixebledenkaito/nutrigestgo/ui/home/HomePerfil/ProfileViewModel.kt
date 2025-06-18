package com.deixebledenkaito.nutrigestgo.ui.home.HomePerfil

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deixebledenkaito.nutrigestgo.domain.model.Formulari
import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: FormulariRepository
) : ViewModel() {

    private val _formulari = mutableStateOf<Formulari?>(null)
    val formulari: State<Formulari?> = _formulari

    fun carregarFormulari(userEmail: String) {
        viewModelScope.launch {
            _formulari.value = repository.getFormulari(userEmail)
        }
    }
}