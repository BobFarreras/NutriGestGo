package com.deixebledenkaito.nutrigestgo.domain.di

import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.LoginUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.LogoutUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.auth.SignupUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.formulari.CheckFormCompletedUseCase
import com.deixebledenkaito.nutrigestgo.domain.usecase.formulari.SaveFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    // 0000000000000000000000000000 MODUL DE L'AUTENTIFICACIO  EMPRESA  00000000000000000000000
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
    @Provides
    fun provideSignupUseCase(repository: AuthRepository): SignupUseCase {
        return SignupUseCase(repository)
    }
    @Provides
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }
    // 0000000000000000000000000000 MODUL DEL FORMULARI  00000000000000000000000

    @Provides
    fun provideCheckFormCompletedUseCase(repository: FormulariRepository): CheckFormCompletedUseCase {
        return CheckFormCompletedUseCase(repository)
    }

    @Provides
    fun provideSaveFormUseCase(repository: FormulariRepository): SaveFormUseCase {
        return SaveFormUseCase(repository)
    }



}