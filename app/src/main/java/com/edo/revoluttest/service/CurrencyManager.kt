package com.edo.revoluttest.service

import com.edo.revoluttest.service.data.CurrenciesData
import com.edo.revoluttest.service.data.CurrenciesName
import io.reactivex.Observable

interface CurrencyManager {

    fun getRates(base: String): Observable<CurrenciesData>
    fun getNames(): Observable<CurrenciesName>

}