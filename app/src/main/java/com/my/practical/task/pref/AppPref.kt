package com.my.practical.task.pref

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref
import com.my.practical.task.model.responses.UserData

object AppPref : KotprefModel() {

    fun clearPref() {
        AppPref.preferences.edit().clear().apply()
        clear()
    }

    var IS_LOGIN by booleanPref(false)
    var GET_LOGIN_DATA by gsonNullablePref<UserData>()
    var AUTH_TOKEN by stringPref("")
    var OTP_TOKEN by stringPref("")
    var RECENT_LIST by gsonNullablePref<ArrayList<String>>(ArrayList())
}