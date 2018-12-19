package com.stingerzou.mygithub.user

import io.reactivex.Observable
import retrofit2.http.GET


interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

}