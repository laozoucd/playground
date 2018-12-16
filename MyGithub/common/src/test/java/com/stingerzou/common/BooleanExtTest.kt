package com.stingerzou.common

import org.junit.Assert
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testBoolean() {
        val resultOtherwise = false.yes{
            1
        }.otherwise{
            2
        }
        Assert.assertEquals(resultOtherwise, 2)
        val result = true.no{
            1
        }.otherwise{
            2
        }
        Assert.assertEquals(result, 2)
    }

    fun getABoolean() = false
}