package com.stingerzou.mygithub.auth

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.stingerzou.mygithub.R
import com.stingerzou.mygithub.app.AccountManager
import com.stingerzou.mygithub.user.UserApi
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mAuthApi: AuthApi

    lateinit var mTxtUsername: AutoCompleteTextView
    lateinit var mTxtPassword: EditText
    lateinit var mBtnSignIn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mTxtUsername = findViewById(R.id.txt_username)
        mTxtPassword = findViewById(R.id.txt_password)
        mBtnSignIn = findViewById(R.id.btn_sign_in)

        mBtnSignIn.setOnClickListener {
            AccountManager.username = mTxtUsername.text.toString()
            AccountManager.password = mTxtPassword.text.toString()

            mAuthApi.createAuthorization(AuthorizationReq())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribe({
                        Log.d("zzh",it.token)
                    },
                    {
                        it.printStackTrace()
                    }
                    )
        }

    }

}
