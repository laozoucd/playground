package com.stingerzou.mygithub.utils

import com.stingerzou.common.Preference
import com.stingerzou.mygithub.app.AppContext

inline fun <reified R, T> R.config(default: T) = Preference(AppContext, default, Preference.CONFIG)
inline fun <reified R, T> R.default(default: T) = Preference(AppContext, default, Preference.DEFAULT)