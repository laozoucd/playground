package com.stingerzou.mygithub.app.di


import com.stingerzou.common.ensureDir
import com.stingerzou.mygithub.app.AppContext
import com.stingerzou.mygithub.app.AuthInterceptor
import com.stingerzou.mygithub.user.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
 class RepositoryModule {

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
                        .addInterceptor(AuthInterceptor())
                        .build())
                .baseUrl(BASE_URL)
                .build()
    }

    @Provides
    fun provideUserApi(retrofit:Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}