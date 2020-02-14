package com.edo.revoluttest.service.currencies

import com.edo.revoluttest.service.data.CurrenciesData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesService {

    @GET("latest")
    fun getRates(
        @Query(
            "base"
        ) base: String
    ):  Observable<CurrenciesData>

}