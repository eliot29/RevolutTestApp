package com.edo.revoluttest.service.model

import com.google.gson.annotations.SerializedName

data class CurrenciesModel (
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)