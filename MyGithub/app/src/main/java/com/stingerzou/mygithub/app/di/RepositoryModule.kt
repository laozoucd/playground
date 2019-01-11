package com.stingerzou.mygithub.app.di


import com.stingerzou.common.ensureDir
import com.stingerzou.mygithub.app.AppContext
import com.stingerzou.mygithub.app.AuthInterceptor
import com.stingerzou.mygithub.user.UserApi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory2
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory2.createWithScheduler(Schedulers.io(), AndroidSchedulers.mainThread()))
                .client(OkHttpClient.Builder()
                        .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                        .addInterceptor(AuthInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(BASE_URL)
                .build()
    }

}