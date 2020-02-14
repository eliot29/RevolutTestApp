package com.edo.revoluttest.di.app

import com.edo.revoluttest.di.modules.ApplicationModule
import com.edo.revoluttest.di.modules.ServiceModule
import com.edo.revoluttest.di.modules.ViewModelModule
import com.edo.revoluttest.view.CurrenciesFragment
import com.edo.revoluttest.view.CurrenciesRecyclerViewAdapter
import com.edo.revoluttest.viewmodel.CurrenciesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class, ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(currenciesViewModel: CurrenciesViewModel)
    fun inject(currencyFragment: CurrenciesFragment)
    fun inject(currenciesRecyclerViewAdapter: CurrenciesRecyclerViewAdapter)

}