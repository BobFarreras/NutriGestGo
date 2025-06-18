package com.deixebledenkaito.nutrigestgo.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.LoginUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.LogoutUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.SignupUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.formulari.CheckFormCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase,
    private val logoutUseCase: LogoutUseCase,

) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("") // Nom per al registre
    var isLogin by mutableStateOf(true)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    fun onAuthClick(onSuccess: (isRegister: Boolean) -> Unit) {
        if (email.isBlank() || password.isBlank() || (!isLogin && name.isBlank())) {
            errorMessage = "Tots els camps són obligatoris"
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = if (isLogin) {
                loginUseCase(email, password)
            } else {
                signupUseCase(email, password, name)
            }
            isLoading = false

            result.fold(
                onSuccess = { onSuccess(!isLogin) }, // si no és login = registre
                onFailure = { errorMessage = it.localizedMessage ?: "Error inesperat" }
            )
        }
    }
    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                logoutUseCase()
                onLogoutSuccess()
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Error en tancar sessió"
            }
        }
    }
    fun toggleMode() {
        isLogin = !isLogin
        errorMessage = null
    }
}
