package com.edo.revoluttest.service.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CurrenciesNameDeserializer : JsonDeserializer<CurrenciesName> {

    override fun deserialize(
        json: JsonElement,
        type: Type?,
        jDcontext: JsonDeserializationContext?
    ): CurrenciesName? {

        val namesJsonObject = json.asJsonObject

        var namesMap = HashMap<String, String>()

        if (namesJsonObject != null) {
            for (key in namesJsonObject.keySet()) {
                var rateJsonElement = namesJsonObject.get(key)
                namesMap.put(key, rateJsonElement.asString)
            }
        }

        return CurrenciesName(namesMap)

    }

}