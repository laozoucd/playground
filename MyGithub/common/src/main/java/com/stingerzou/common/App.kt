package com.stingerzou.common

import android.app.Application


lateinit var AppContext: Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext = this
        Preference.init(this)
    }

}

