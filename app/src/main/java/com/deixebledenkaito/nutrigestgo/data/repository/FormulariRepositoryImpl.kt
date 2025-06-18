package com.deixebledenkaito.nutrigestgo.data.repository

import com.deixebledenkaito.nutrigestgo.domain.model.Formulari
import com.deixebledenkaito.nutrigestgo.domain.repository.FormulariRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FormulariRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FormulariRepository {

    override suspend fun saveForm(userEmail: String, formulari: Formulari): Result<Unit> {
        return try {
            val docRef = firestore.collection("Usuaris")
                .document(userEmail)
                .collection("formulari")
                .document("info")

            val dades = mapOf(
                "tempsCuinar" to formulari.tempsCuinar,
                "pressupost" to formulari.pressupost,
                "alergies" to formulari.alergies,
                "nutricio" to formulari.nutricio,
                "objectiuSalut" to formulari.objectiuSalut,
                "restriccio" to formulari.restriccio
            )

            docRef.set(dades).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasCompletedForm(userEmail: String): Boolean {
        return try {
            val docRef = firestore.collection("Usuaris")
                .document(userEmail)
                .collection("formulari")
                .document("info")
                .get()
                .await()

            docRef.exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getFormulari(userEmail: String): Formulari? {
        return try {
            val doc = firestore.collection("Usuaris")
                .document(userEmail)
                .collection("formulari")
                .document("info")
                .get()
                .await()

            if (doc.exists()) {
                doc.toObject(Formulari::class.java)
            } else null
        } catch (e: Exception) {
            null
        }
    }
}