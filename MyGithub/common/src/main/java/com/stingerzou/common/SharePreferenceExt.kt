package com.stingerzou.common

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(val context:Context,
                    val default:T,
                    val prefName:String = "default") : ReadWriteProperty<Any?, T> {

    //Preference 分类
    companion object {
        const val DEFAULT:String = "default"
        const val CONFIG:String = "config"

        //在进入软件时，加载 xml 文件
        //阅读源码后发现 这是新开线程操作，基本不影响软件打开速度
        fun init(context: Context) {
            context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE)
            context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE)
        }
    }

    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {

        val key = property.name

        return when(default) {
            is Int -> prefs.getInt(key,default)
            is Long -> prefs.getLong(key,default)
            is Boolean -> prefs.getBoolean(key,default)
            is String -> prefs.getString(key, default)
            is Float -> prefs.getFloat(key, default)
            else -> throw IllegalArgumentException("Unsupport type")
        } as T

    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        val key = property.name

        with(prefs.edit()) {
            when(value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("Unsupport type")
            }
        }.apply()
    }
}

inline fun <reified R, T> R.configProxy(default: T) = Preference(AppContext, default,Preference.CONFIG)
inline fun <reified R, T> R.defaultProxy(default: T) = Preference(AppContext, default,Preference.DEFAULT)