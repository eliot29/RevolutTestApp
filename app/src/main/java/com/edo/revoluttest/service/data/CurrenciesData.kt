package com.edo.revoluttest.service.data

data class CurrenciesData (
    val base: String? = null,
    val date: String? = null,
    val currencies: MutableCollection<Currency>
)