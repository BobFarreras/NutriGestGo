package com.deixebledenkaito.nutrigestgo.ui.home.homeReceptes

import com.deixebledenkaito.nutrigestgo.domain.model.Recepta

fun generarSetmanaReceptes(
    totesReceptes: List<Recepta>,
    alergiesUsuari: List<String>
): Map<String, List<Recepta>> {
    val dies = listOf("Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte", "Diumenge")
    val setmana = mutableMapOf<String, List<Recepta>>()

    val receptesFiltrades = totesReceptes.filter { recepta ->
        alergiesUsuari.none { alergia ->
            recepta.ingredients.any { it.contains(alergia, ignoreCase = true) }
        }
    }

    dies.forEach { dia ->
        val esmorzar = receptesFiltrades.filter { it.tipus == "esmorzar" }.randomOrNull()
        val dinar = receptesFiltrades.filter { it.tipus == "dinar" }.randomOrNull()
        val sopar = receptesFiltrades.filter { it.tipus == "sopar" }.randomOrNull()
        setmana[dia] = listOfNotNull(esmorzar, dinar, sopar)
    }

    return setmana
}