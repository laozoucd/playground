package com.stingerzou.mygithub.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityModule {
    @Provides
    fun provideAuthApi(retrofit:Retrofit):AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}