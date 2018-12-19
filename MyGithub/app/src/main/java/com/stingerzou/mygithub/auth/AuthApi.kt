package com.stingerzou.mygithub.auth

import com.stingerzou.mygithub.app.AccountManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path


interface AuthApi{

    @PUT("/authorizations/clients/${AccountManager.clientId}/{fingerPrint}")
    fun createAuthorization(@Body req: AuthorizationReq, @Path("fingerPrint") fingerPrint: String = AccountManager.fingerPrint)
            : Observable<AuthorizationRsp>

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>

}