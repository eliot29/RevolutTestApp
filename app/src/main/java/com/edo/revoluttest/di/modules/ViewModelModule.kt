package com.edo.revoluttest.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.edo.revoluttest.view.CurrenciesRecyclerViewAdapter
import com.edo.revoluttest.viewmodel.CurrenciesViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ViewModelModule(private var application: Application) {

    @Singleton
    @Provides
    fun provideCurrenciesViewModel() =
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CurrenciesViewModel::class.java)

    @Singleton
    @Provides
    fun provideScheduler(): Scheduler = AndroidSchedulers.mainThread()


    @Singleton
    @Provides
    fun provideAdapter(): CurrenciesRecyclerViewAdapter = CurrenciesRecyclerViewAdapter()
}