package com.edo.revoluttest.di.app

import android.app.Application
import com.edo.revoluttest.di.modules.ApplicationModule
import com.edo.revoluttest.di.modules.ServiceModule
import com.edo.revoluttest.di.modules.ViewModelModule

object Injector {

    lateinit var appComponent: ApplicationComponent

    fun initAppComponent(application: Application) {

        appComponent = DaggerApplicationComponent.builder()
            .serviceModule(ServiceModule())
            .viewModelModule(ViewModelModule(application))
            .applicationModule(ApplicationModule(application))
            .build()

    }

}