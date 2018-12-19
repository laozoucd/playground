package com.stingerzou.mygithub.app

import com.stingerzou.mygithub.utils.default
import com.stingerzou.mygithub.utils.deviceId

//账号相关信息
object AccountManager {

    val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
    const val clientId = "11a34b18322306b6d904"
    const val clientSecret = "bc5558fcd3dfec98adfce48086844b083cbbf0d3"
    const val note = ""
    const val noteUrl = ""
    val fingerPrint by lazy {
        AppContext.deviceId + clientId
    }

    lateinit var username:String
    lateinit var password:String

    var token:String by default("")

    fun isLoginIn():Boolean {
        return token.isEmpty()
    }
}