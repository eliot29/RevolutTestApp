package com.edo.revoluttest.service.currencies

import com.edo.revoluttest.service.data.CurrenciesData
import com.edo.revoluttest.service.data.CurrenciesName
import io.reactivex.Observable
import retrofit2.http.GET

interface CurrenciesNameService {

    @GET("https://api.myjson.com/bins/myu94")
    fun getRates(): Observable<CurrenciesName>

}