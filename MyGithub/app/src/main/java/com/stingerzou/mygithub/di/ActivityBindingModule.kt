package com.stingerzou.mygithub.di

import com.stingerzou.mygithub.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainActivity():MainActivity
}