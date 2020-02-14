package com.edo.revoluttest.service.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<CurrenciesData> {

    override fun deserialize(
        json: JsonElement,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?
    ): CurrenciesData? {

        val currenciesJsonObject = json.asJsonObject

        val ratesJsonObject = currenciesJsonObject?.get("rates")?.asJsonObject
        var rates = mutableListOf<Currency>()

        if (ratesJsonObject != null) {
            for (key in ratesJsonObject.keySet()) {
                var rateJsonElement = ratesJsonObject.get(key)
                rates.add(
                    Currency(
                        currencyCode = key,
                        rate = rateJsonElement.asDouble,
                        name = ""
                    )
                )
            }
        }

        return CurrenciesData(
            base = currenciesJsonObject?.get("base")?.asString,
            date = currenciesJsonObject?.get("date")?.asString,
            currencies = rates
        )

    }

}