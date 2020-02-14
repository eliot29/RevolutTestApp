package com.edo.revoluttest.di.modules

import com.edo.revoluttest.service.CurrencyManager
import com.edo.revoluttest.service.CurrencyManagerImpl
import com.edo.revoluttest.service.currencies.CurrenciesNameService
import com.edo.revoluttest.service.currencies.CurrenciesService
import com.edo.revoluttest.service.data.CurrenciesData
import com.edo.revoluttest.service.data.CurrenciesName
import com.edo.revoluttest.service.data.CurrenciesNameDeserializer
import com.edo.revoluttest.service.data.CurrencyDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ServiceModule() {

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(CurrenciesData::class.java, CurrencyDeserializer())
            .registerTypeAdapter(CurrenciesName::class.java, CurrenciesNameDeserializer())
            .create()


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://revolut.duckdns.org/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideCurrenciesService(retrofit: Retrofit): CurrenciesService =
        retrofit.create<CurrenciesService>(CurrenciesService::class.java)

    @Provides
    fun provideCurrenciesNameService(retrofit: Retrofit): CurrenciesNameService =
        retrofit.create<CurrenciesNameService>(CurrenciesNameService::class.java)

    @Provides
    fun provideCurrencyManager(currenciesService: CurrenciesService,
                               currenciesNameService: CurrenciesNameService): CurrencyManager =
        CurrencyManagerImpl(currenciesService, currenciesNameService)

}