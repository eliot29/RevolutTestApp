package com.edo.revoluttest.service

import com.edo.revoluttest.service.currencies.CurrenciesNameService
import com.edo.revoluttest.service.currencies.CurrenciesService
import com.edo.revoluttest.service.data.CurrenciesData
import com.edo.revoluttest.service.data.CurrenciesName
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CurrencyManagerImpl (private val currenciesService: CurrenciesService,
                           private val currenciesNameService: CurrenciesNameService): CurrencyManager {

    override fun getRates(base: String): Observable<CurrenciesData> {
        return currenciesService.getRates(base)
            .subscribeOn(Schedulers.io())
    }

    override fun getNames(): Observable<CurrenciesName> {
        return currenciesNameService.getRates()
            .subscribeOn(Schedulers.io())
    }

}