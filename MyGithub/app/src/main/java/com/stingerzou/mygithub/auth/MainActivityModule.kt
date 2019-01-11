package com.stingerzou.mygithub.auth

import com.stingerzou.mygithub.app.di.ActivityScoped
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {

    @ActivityScoped
    @Provides
    fun provideAuthApi(retrofit:Retrofit):AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}