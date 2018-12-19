package com.stingerzou.mygithub.auth

import com.stingerzou.mygithub.app.AccountManager
import com.stingerzou.mygithub.app.PoKo


/**
 * Created by benny on 6/21/17.
 */
@PoKo
data class AuthorizationReq(var scopes: List<String> = AccountManager.SCOPES,
                            var note: String = AccountManager.note,
                            var note_url: String = AccountManager.noteUrl,
                            var client_secret: String = AccountManager.clientSecret)

@PoKo
data class AuthorizationRsp(var id: Int,
                            var url: String,
                            var app: App,
                            var token: String,
                            var hashed_token: String,
                            var token_last_eight: String,
                            var note: String,
                            var note_url: String,
                            var created_at: String,
                            var updated_at: String,
                            var scopes: List<String>)

@PoKo
data class App(var name: String, var url: String, var client_id: String)