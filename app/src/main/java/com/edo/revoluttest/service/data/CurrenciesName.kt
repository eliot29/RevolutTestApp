package com.edo.revoluttest.service.data

data class CurrenciesName(
    var names: HashMap<String, String>
) {
    operator fun get(currencyCode: String): String? {
        return names[currencyCode]
    }
}