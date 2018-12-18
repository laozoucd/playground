package com.stingerzou.mygithub.net

import com.stingerzou.mygithub.auth.User
import io.reactivex.Observable
import retrofit2.http.GET


interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

}