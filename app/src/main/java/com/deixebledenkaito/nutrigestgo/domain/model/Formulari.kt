package com.deixebledenkaito.nutrigestgo.domain.model

data class Formulari(
    val tempsCuinar: Int = 0,
    val pressupost: Double = 0.0,
    val alergies: List<String> = emptyList(),
    val nutricio: String? = "Omnívora",
    val objectiuSalut: String? = null,
    val restriccio: String? = null
)