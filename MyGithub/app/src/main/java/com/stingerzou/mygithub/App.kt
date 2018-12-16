package com.stingerzou.mygithub

import android.app.Application
import android.content.ContextWrapper
import com.stingerzou.common.Preference


private lateinit var INSTANCE: Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Preference.configure(this)
    }

}

object AppContext: ContextWrapper(INSTANCE)