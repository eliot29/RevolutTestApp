package com.edo.revoluttest.di.app

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.initAppComponent(this);
    }

}