package com.stingerzou.mygithub.app.di

import android.app.Application
import com.stingerzou.mygithub.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    RepositoryModule::class,
    ApplicationModule::class])
interface AppComponent:AndroidInjector<App>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}