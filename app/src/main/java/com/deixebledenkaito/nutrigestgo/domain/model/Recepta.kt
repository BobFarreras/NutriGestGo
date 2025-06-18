package com.deixebledenkaito.nutrigestgo.domain.model

data class Recepta(
    val nom: String,
    val ingredients: List<String>,
    val tipus: String // "esmorzar", "dinar", "sopar"
)