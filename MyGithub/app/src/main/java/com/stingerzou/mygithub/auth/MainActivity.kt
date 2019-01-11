package com.stingerzou.mygithub.auth

import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.stingerzou.mygithub.R
import com.stingerzou.mygithub.app.AccountManager
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mAuthApi: AuthApi

    private lateinit var mTxtUsername: AutoCompleteTextView
    private lateinit var mTxtPassword: EditText
    private lateinit var mBtnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mTxtUsername = findViewById(R.id.txt_username)
        mTxtPassword = findViewById(R.id.txt_password)
        mBtnSignIn = findViewById(R.id.btn_sign_in)

        RxJavaPlugins.setErrorHandler{
            Log.d("zzh",  "hook ${it.message}")
        }

        mBtnSignIn.setOnClickListener {
            AccountManager.username = mTxtUsername.text.toString()
            AccountManager.password = mTxtPassword.text.toString()

            mAuthApi.createAuthorization(AuthorizationReq())
                    .subscribe({
                        Log.d("zzh", it.token)
                    }, {
                        Log.d("zzh",  "real ${it.message}")
                        it.printStackTrace()
                    }
                    )
        }


//        RxJavaPlugins.setOnMaybeSubscribe{maybe,maybeObserver ->
//            WrapDownStreamObserver(maybeObserver as MaybeObserver<Int>)
//        }
//
//        Maybe.just(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.d("zzh", """Real onSuccess $it""")
//                },{
//                    Log.d("zzh", "Real onError")
//                })
    }
}


class WrapDownStreamObserver : MaybeObserver<Int> {

    private lateinit var actual:MaybeObserver<Int>

    constructor(actual: MaybeObserver<Int>) {
        this.actual = actual
    }


    override fun onSubscribe(d: Disposable) {
        actual.onSubscribe(d)
    }

    override fun onSuccess(t: Int) {
        Log.d("zzh", """Hooked onSuccess$t""")
        actual.onSuccess(t + 1)
    }

    override fun onError(e: Throwable) {
        Log.d("zzh", "Hooked onError")
        actual.onError(e)
    }

    override fun onComplete() {
        Log.d("zzh", "Hooked onComplete");
        actual.onComplete()
    }

}




