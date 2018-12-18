package com.stingerzou.mygithub.di

import com.stingerzou.common.AppContext
import com.stingerzou.common.ensureDir
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    private val BASE_URL = "https://api.github.com"

    private val cacheFile by lazy {
        File(AppContext.cacheDir, "webServiceApi").apply { ensureDir() }
    }

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit {

      return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder()
                        .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                        .build())
                .baseUrl(BASE_URL)
                .build()
    }
}