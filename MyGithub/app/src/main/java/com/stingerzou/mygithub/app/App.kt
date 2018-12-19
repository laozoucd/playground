package com.stingerzou.mygithub.app

import android.app.Application
import com.stingerzou.common.Preference
import com.stingerzou.mygithub.app.di.DaggerAppComponent

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


lateinit var AppContext: Application

class App:DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        AppContext = this
        Preference.init(this)
    }

}

