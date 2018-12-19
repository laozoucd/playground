package com.stingerzou.mygithub

import android.support.test.runner.AndroidJUnit4
import com.stingerzou.mygithub.app.AppConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetttingsTest {

    @Test
    fun testSetting() {
        val userName:String = "jack"
        val passWord:String = "123456"
        AppConfig.userName = userName
        AppConfig.passWorld = passWord

        Assert.assertEquals(AppConfig.userName, userName)
        Assert.assertEquals(AppConfig.passWorld, passWord)
    }

}