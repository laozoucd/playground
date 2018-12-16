package com.stingerzou.mygithub

import com.stingerzou.common.Preference

object Settings {
    var userName:String by Preference(AppContext,"",Preference.SETTINGS)
    var passWorld:String by Preference(AppContext,"",Preference.SETTINGS)
}
