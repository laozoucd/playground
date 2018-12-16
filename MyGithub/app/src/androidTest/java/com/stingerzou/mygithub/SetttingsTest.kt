package com.stingerzou.mygithub

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetttingsTest {

    @Test
    fun testSetting() {
        val userName:String = "jack"
        val passWord:String = "123456"
        Settings.userName = userName
        Settings.passWorld = passWord

        Assert.assertEquals(Settings.userName, userName)
        Assert.assertEquals(Settings.passWorld, passWord)
    }

}