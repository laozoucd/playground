package com.stingerzou.mygithub.app.di

import com.stingerzou.mygithub.auth.MainActivity
import com.stingerzou.mygithub.auth.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun mainActivity(): MainActivity
}