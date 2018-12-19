package com.stingerzou.mygithub.auth

import android.os.Bundle
import com.stingerzou.mygithub.R
import com.stingerzou.mygithub.user.UserApi
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Scheduler
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mAuthApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
