package com.deixebledenkaito.nutrigestgo.data.di

import com.deixebledenkaito.nutrigestgo.data.repository.AuthRepositoryImpl
import com.deixebledenkaito.nutrigestgo.data.repository.FormulariRepositoryImpl
import com.deixebledenkaito.nutrigestgo.domain.repository.AuthRepository
import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //    LOGICA AUTENTIFICACIÓ
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }


    //    FORMULARI
    @Provides
    @Singleton
    fun provideFormulariRepository(firestore: FirebaseFirestore): FormulariRepository {
        return FormulariRepositoryImpl(firestore)
    }
}