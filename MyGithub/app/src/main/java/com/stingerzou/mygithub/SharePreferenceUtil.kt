package com.stingerzou.mygithub

import com.stingerzou.common.Preference

inline fun <reified R, T> R.config(default: T) = Preference(AppContext, default, Preference.CONFIG)
inline fun <reified R, T> R.default(default: T) = Preference(AppContext, default, Preference.DEFAULT)