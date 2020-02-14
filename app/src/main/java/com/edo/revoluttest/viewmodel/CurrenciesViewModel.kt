package com.edo.revoluttest.viewmodel

import androidx.lifecycle.ViewModel
import com.edo.revoluttest.di.app.Injector
import com.edo.revoluttest.service.CurrencyManager
import com.edo.revoluttest.service.data.CurrenciesData
import com.edo.revoluttest.service.data.CurrenciesName
import com.edo.revoluttest.service.data.Currency
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class CurrenciesViewModel : ViewModel() {

    @Inject
    lateinit var currenciesManager: CurrencyManager

    @Inject
    lateinit var scheduler: Scheduler

    private var currenciesSubject = BehaviorSubject.create<List<Currency>>()

    private var latestBaseSubject = BehaviorSubject.create<Currency>()

    private var latestSubject = BehaviorSubject.create<Currency>()

    var currencyDefault = Currency("EUR", 1.00, "Euro")

    private lateinit var currenciesName: CurrenciesName

    private lateinit var currenciesDisposable: Disposable

    init {
        init()
    }

    fun getCurrenciesSubject() = currenciesSubject

    fun init() {
        Injector.appComponent.inject(this)
        currenciesDisposable = getCurrenciesObservable().subscribe({
                currencies -> currenciesSubject.onNext(currencies)
        })
    }

    private fun getCurrenciesObservable(): Observable<ArrayList<Currency>>{

        val obsCurrencies = Observable.combineLatest<CurrenciesData, CurrenciesName, Currency, ArrayList<Currency>>(
            getRatesObservable(),
            getCurrenciesNameObservable(),
            getBaseCurrencySubject(),
            Function3 {
                    rates, names, currency ->
                var currencies = getCurrenciesWithNames(rates, names, currency)
                currencies
            }).observeOn(scheduler)

        return obsCurrencies

    }

    private fun getCurrenciesWithNames(currenciesData: CurrenciesData,
                                       currenciesName: CurrenciesName,
                                       baseCurrency: Currency): ArrayList<Currency> {
        this.currenciesName = currenciesName
        val currencies = ArrayList<Currency>()

        for (currency in currenciesData.currencies) {
            currency.name = currenciesName.get(currency.currencyCode)!!
            if(currency.currencyCode != baseCurrency.currencyCode) currency.rate *= baseCurrency.rate
        }
        //currencies.add(baseCurrency)
        currencies.addAll(currenciesData.currencies)
        return currencies
    }

    private fun getRatesObservable(): Observable<CurrenciesData>? {
        return Observable.combineLatest<Currency, Long, String>(
            latestBaseSubject,
            Observable.interval(0, 1, TimeUnit.SECONDS),
            BiFunction { currency, long -> currency.currencyCode })
            .flatMap { currenciesManager.getRates(getBaseCurrency().currencyCode) }
    }

    fun getBaseCurrencySubject(): BehaviorSubject<Currency> {
        return this.latestBaseSubject
    }

    fun getLatestSubject(): BehaviorSubject<Currency> {
        return this.latestSubject
    }

    private fun getCurrenciesNameObservable(): Observable<CurrenciesName>? {
        return currenciesManager.getNames();
    }

    private fun getBaseCurrency(): Currency {
        return latestBaseSubject.value ?: currencyDefault
    }

    fun onStop() {
        currenciesDisposable?.dispose()
    }
}
