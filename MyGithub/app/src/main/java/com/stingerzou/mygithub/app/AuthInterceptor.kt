package com.stingerzou.mygithub.app

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {

            when {
                originalRequest.url().pathSegments().contains("authorizations") -> {
                    val userCredentials = AccountManager.username + ":" + AccountManager.password
                    val auth = "Basic " + String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT)).trim()
                    header("Authorization", auth)
                }

                AccountManager.isLoginIn() -> {
                    val auth = "Token " + AccountManager.token
                    header("Authorization", auth)
                }

                else -> removeHeader("Authorization")
            }
        }.build()

        return chain.proceed(newRequest)
    }

}